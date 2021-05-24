/**
 *
 */
package com.openmatics.omob.clientlogin.management.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;

import com.openmatics.omob.clientlogin.AbstractIntegrationTest;
import com.openmatics.omob.clientlogin.management.dto.UserDTO;

/**
 * @author KA70160
 *
 */
public class GetUserByNameTest extends AbstractIntegrationTest {

	/** Logger for this class. */
	private static final Logger LOGGER = LoggerFactory.getLogger(GetUserByNameTest.class);

	/** restTemplate. */
	@Autowired
	private TestRestTemplate restTemplate;

	/** URL. */
	private static final String URL = "/user";

	/**
	 * testGetUserByName. @ throws Exception exception
	 */
	@Test
	public void testGetUserByNameExists() throws Exception {

		restTemplate.getRestTemplate().getInterceptors().add(new BasicAuthorizationInterceptor("katherbasha", "test"));
		ResponseEntity<UserDTO> responseEntity = restTemplate.getForEntity(URL + "/{username}", UserDTO.class,
				"katherbasha");
		LOGGER.info(" responseEntity = " + responseEntity);
		// collect response
		int status = responseEntity.getStatusCodeValue();
		UserDTO user = responseEntity.getBody();
		// verify
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
		assertNotNull(user);

	}

}
