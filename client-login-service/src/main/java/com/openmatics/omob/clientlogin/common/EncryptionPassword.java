/**
 *
 * Copyright Notice: Openmatics s.r.o -- Confidential and Proprietary
 *
 * All rights reserved.
 * This software is the confidential and proprietary information of Openmatics s.r.o
 * ("Confidential Information"). You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the license agreement you
 * entered into with Openmatics.
 */
package com.openmatics.omob.clientlogin.common;


import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * This class used to encrypt and verify the given password using BCrypt strong hashing algorithm.
 *
 * @author Lipika
 *
 */
public class EncryptionPassword  {

  /**
   * constructor.
   */
  protected EncryptionPassword() { }

  /**
   * The method is used to encrypt the given password.
   * @param rawPassword  plain text password to encrypt
   * @return the encrypted password
   */
  public static String encode(String rawPassword) {

    return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
  }

  /**
   * Check that a plaintext password matches a previously hashed one.
   * @param rawPassword the plaintext password to verify
   * @param encodedPassword the previously-hashed password
   * @return true if the passwords match, false otherwise.
   */
  public static boolean matches(String rawPassword, String encodedPassword) {

    return BCrypt.checkpw(rawPassword, encodedPassword);
  }
}

