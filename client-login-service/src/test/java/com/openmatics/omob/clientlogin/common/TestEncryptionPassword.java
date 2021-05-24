package com.openmatics.omob.clientlogin.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * TestEncryptionPassword test class.
 *
 */
public class TestEncryptionPassword {

  /**
   * testMatchesVerify() test method to test password encryption.
   */
  @Test
  public void testMatchesVerify() {
    //boolean result = EncryptionPassword.matches("lipika", "$2a$10$q6Cm.d6CAZ1nxcL8HzfKVO.BFQDaRRNLbHwC1xgJslFtq1MbF9to.");
	  boolean result = EncryptionPassword.matches("test", "$2a$10$.wExXtpCZ8wGUaTuyiZ4hO.Bk6.3SVcL9J9UMZe1Vl1OCGF2hPHsa");
    assertEquals(true, result);
  }

}


