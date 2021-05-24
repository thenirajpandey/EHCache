package com.openmatics.omob.clientlogin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.openmatics.omob.clientlogin.persistanceobject.ClientMetaData;

/**
 * Repository for ClientMetaData  implemented using Spring Data JPA.
 * @author Sumalatha Vadanala
 *
 */
@Repository
public interface ClientMetaDataDAO extends JpaRepository<ClientMetaData, Long> {

      /**
       * Reading Client Meta data for the given Client Code.
       * @param clientCode Client Code of the Client
       * @return ClientMetaData Meta data of the client
       */
      ClientMetaData findByClientCode(String clientCode);

}
