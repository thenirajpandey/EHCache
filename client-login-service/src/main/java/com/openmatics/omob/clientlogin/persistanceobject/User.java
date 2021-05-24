package com.openmatics.omob.clientlogin.persistanceobject;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This Class is the Entity Object for User.
 * User details will be defined with its setters/getters.
 *
 * @author Sumalatha Vadanala
 */
@Entity
@Table(name = "USER_DETAILS")
public class User implements Serializable {
  /**
   * serialVersionUID.
   */
  private static final long serialVersionUID = 1L;
  /**
   * ID for Client.
   */
  private int id;
  /**
   * USERNAME for Client.
   */
  private String userName;
  /**
   * PASSWORD for userName.
   */
  private String password;
  /**
   * Admin previllege for user.
   */
  private boolean admin;

  /**
   * ClientMetaData Object for having foreignkey.
   */
  private ClientMetaData clientMetaData;
  /**
   * Default Constructor for JPA.
   */
  public User() {
  }
  /**
   * Parameterized Constructor for JPA.
   * @param userName for Client.
   * @param password for userName.
   */
  public User(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }
  /**
   * Parameterized Constructor for JPA.
   * @param userName for Client.
   * @param password for userName.
   * @param clientMetaData for Client.
   * @param admin user scope as Admin will be described as true/false.
   */
  public User(String userName, String password, ClientMetaData clientMetaData, boolean admin) {
    this.userName = userName;
    this.password = password;
    this.clientMetaData = clientMetaData;
    this.admin = admin;
  }
  @SuppressWarnings("checkstyle:javadocmethod")
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID")
  public int getId() {
    return id;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  public void setId(int id) {
    this.id = id;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  @Column(name = "USERNAME", nullable = false, unique = true)
  public String getUserName() {
    return userName;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  public void setUserName(String userName) {
    this.userName = userName;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  @Column(name = "PASSWORD", nullable = false)
  public String getPassword() {
    return password;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  public void setPassword(String password) {
    this.password = password;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  @ManyToOne(optional = false)
  @JoinColumn(name = "CLIENT_METADATA_ID", referencedColumnName = "ID")
  public ClientMetaData getClientMetaData() {
    return clientMetaData;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  public void setClientMetaData(ClientMetaData clientMetaData) {
    this.clientMetaData = clientMetaData;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  @Column(name = "ADMIN", nullable = false)
  public boolean isAdmin() {
    return admin;
  }

  @SuppressWarnings("checkstyle:javadocmethod")
  public void setAdmin(boolean admin) {
    this.admin = admin;
  }
  /**
   * Override method for Object User.
   */
  @Override
  public String toString() {
      return new ToStringBuilder(this).
                 append("id", id).
                 append("userName", userName).
                 append("admin", admin).
                 toString();

  }

  /**
   * hashCode implementation of UserDTO.
   */
  @Override
  public int hashCode() {
        return new HashCodeBuilder()
              .append(id)
              .toHashCode();
        }

  /**
   * equals implementation for UserDTO object.
   */
  @Override
  public boolean equals(Object obj) {
      if (this == obj) {
          return true;
      }
      if (obj == null) {
          return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      User other = (User) obj;
            return new EqualsBuilder()
              .append(id, other.id)
              .isEquals();
   }
}

