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
public class GetAllUsersTest extends AbstractIntegrationTest {

	/** Logger for this class. */
	private static final Logger LOGGER = LoggerFactory.getLogger(GetAllUsersTest.class);

	/** restTemplate. */
	@Autowired
	private TestRestTemplate restTemplate;

	/** URL. */
	private static final String URL = "/user";

	/**
	 * testGetAllUsers. @ throws Exception exception
	 */
	@Test
	public void testGetAllUsersExists() throws Exception {

		// HttpEntity<String> entity = new HttpEntity<String>("parameters",
		// getHeaders());
		ResponseEntity<UserDTO[]> responseEntity = restTemplate.getForEntity(URL, UserDTO[].class);
		LOGGER.info(" responseEntity = " + responseEntity);
		// collect response
		int status = responseEntity.getStatusCodeValue();
		UserDTO[] users = responseEntity.getBody();
		// verify
		assertEquals("Incorrect Response Status", HttpStatus.OK.value(), status);
		assertNotNull(users);

	}

}
