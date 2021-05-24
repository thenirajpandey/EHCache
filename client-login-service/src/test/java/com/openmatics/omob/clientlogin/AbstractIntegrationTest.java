/**
 *
 */
package com.openmatics.omob.clientlogin;




import org.junit.Before;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.test.context.junit4.SpringRunner;



/**
 * @author KA70160
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
@Sql({ "classpath:test-data.sql" })
public abstract class AbstractIntegrationTest {
	
    /** Logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractIntegrationTest.class);
	
    /** restTemplate. */
    @Autowired
    private TestRestTemplate restTemplate;

    /** HttpHeaders.
     * @return HttpHeaders http headers
     */
    @Before
    public void doLogin() {
    	LOGGER.info("Entering doLogin");
    	restTemplate.getRestTemplate().getInterceptors().add(new BasicAuthorizationInterceptor("katherbasha", "test"));
    	LOGGER.info("Exiting doLogin");
    }
}
