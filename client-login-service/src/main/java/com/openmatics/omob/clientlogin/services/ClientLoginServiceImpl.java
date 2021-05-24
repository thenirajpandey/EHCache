package com.openmatics.omob.clientlogin.services;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.openmatics.omob.clientlogin.IClientLoginService;
import com.openmatics.omob.clientlogin.common.AuthenticationUtil;
import com.openmatics.omob.clientlogin.dao.ClientMetaDataDAO;
import com.openmatics.omob.clientlogin.dao.UserDAO;
import com.openmatics.omob.clientlogin.dto.ClientMetaDataDto;
import com.openmatics.omob.clientlogin.persistanceobject.ClientMetaData;
import com.openmatics.omob.clientlogin.persistanceobject.User;

/**
 *
 * ClientLoginServiceImpl Object contains implementation methods of clientloginservice api.
 * It is used to authenticate User and fetch client meta information.
 *
 * @author Saisreekanth
 */
@Service
public class ClientLoginServiceImpl implements IClientLoginService {

  /**
   * Logger object for Application logging from this class.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ClientLoginServiceImpl.class); /**
   * User Repository for retrieving User information.
   */
  @Autowired
  private  UserDAO userDAO;
  /**
   * ClientMetaData Repository for retrieving ClientMetaData information.
   */
  @Autowired
  private ClientMetaDataDAO clientMetaDataDAO;

  /**
   *
   * method authenticateUser() used to validate User Credentials.
   *
   */
  @Override
  public void authenticateUser() {
    String userName = AuthenticationUtil.getLoggedInUser();
    LOGGER.info("authenticateUser() User Authentication Successful for user: " + userName);
  }

  @Override
  @Transactional
  public ClientMetaDataDto getClientMetaData() {
    LOGGER.trace("Entering into getClientMetaData method");
    // To hold currentUserName.
    String currentUserName = null;
    // ClientMetaData Object hold meta data about the Client.
    ClientMetaData metaData = null;
    // Presentation DTO for ClientMetaData.
    ClientMetaDataDto dto = null;
    Authentication authentication = AuthenticationUtil.getAuthentication();
    currentUserName = authentication.getName();
    LOGGER.debug("Current UserName :" + currentUserName);

    if (!StringUtils.isBlank(currentUserName)) {
      // Get User Details Object by UserName.
      User userDetails = userDAO.findByUserName(currentUserName);
      // Get ClientMetaData details while passing clientId to ClientMetaDAO.
      metaData = userDetails.getClientMetaData();
      // Transform from ClientMetaData to ClientMetaDataDto.
      dto = transformDTO(metaData);
    }
    LOGGER.trace("Exiting from getClientMetaData method");
    return dto;
  }
  /**
   * Transform ClientMetaData to ClientMetaDataDto.
   * @param metadata as ClientMetaData object
   * @return ClientMetaDataDto of Presentation DTO
   */
  private ClientMetaDataDto transformDTO(ClientMetaData metadata) {
    ClientMetaDataDto dto = new ClientMetaDataDto();
    String clientCode = metadata.getClientCode();
    String clientName = metadata.getClientName();
    String url = metadata.getUrl();
    String logoURL = metadata.getLogoURL();

    dto.setClientCode(clientCode);
    dto.setClientName(clientName);
    dto.setLogoUrl(logoURL);
    dto.setServiceUrl(url);
    return dto;
  }

}
