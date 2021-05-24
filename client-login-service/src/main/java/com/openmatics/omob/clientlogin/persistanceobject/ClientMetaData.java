package com.openmatics.omob.clientlogin.persistanceobject;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Persistence Object for CLIENT_METADATA Table.
 * @author Sumalatha Vadanala
 *
 */
@Entity
@Table(name = "CLIENT_METADATA")
public class ClientMetaData implements Serializable {
    /**
     * Generated serialVersionUID.
     */
    private static final long serialVersionUID = 4578629827130099733L;

    /**
     * Represents ID for the Client.
     */
    private Long clientID;
    /**
     * Represents a code for each Client.
     */
    private String clientCode;
    /**
     * Represents Name for Client.
     */
    private String clientName;
    /**
     * Represents url for Client.
     */
    private String url;
    /**
     * Represents Client logo as URL .
     */
    private String logoURL;
    /**
     * Set of User Entity Objects.
     */
    private Set<User> userdetails;

    /**
     * Default Constructor for JPA.
     */
    public  ClientMetaData() {

    }
    /**
     * Parameterized Constructor for ClientMetaData.
     * @param clientCode as Code for Client.
     * @param clientName as Name of Client.
     * @param url for Client.
     * @param logoURL for Client.
     */
    public ClientMetaData(String clientCode, String clientName, String url, String logoURL) {
         this.url = url;
         this.logoURL = logoURL;
         this.clientCode = clientCode;
         this.clientName = clientName;
    }

    @SuppressWarnings("checkstyle:javadocmethod")
    @Column(name = "CLIENT_CODE", unique = true, nullable = false)
    public String getClientCode() {
       return clientCode;
    }

    @SuppressWarnings("checkstyle:javadocmethod")
    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    @SuppressWarnings("checkstyle:javadocmethod")
    @Column(name = "CLIENT_NAME", nullable = false)
    public String getClientName() {
       return clientName;
    }

    @SuppressWarnings("checkstyle:javadocmethod")
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @SuppressWarnings("checkstyle:javadocmethod")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    public Long getClientID() {
        return clientID;
    }

    @SuppressWarnings("checkstyle:javadocmethod")
    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    @SuppressWarnings("checkstyle:javadocmethod")
    @Column(name = "SERVICE_URL", nullable = false)
    public String getUrl() {
         return url;
    }

    @SuppressWarnings("checkstyle:javadocmethod")
    public void setUrl(String url) {
         this.url = url;
    }

    @SuppressWarnings("checkstyle:javadocmethod")
    @Column(name = "LOGO_URL", nullable = false)
    public String getLogoURL() {
    return logoURL;
    }

    @SuppressWarnings("checkstyle:javadocmethod")
    public void setLogoURL(String logoURL) {
       this.logoURL = logoURL;
    }

    @SuppressWarnings("checkstyle:javadocmethod")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "clientMetaData", cascade = CascadeType.ALL)
    public Set<User> getUser() {
         return userdetails;
    }

    @SuppressWarnings("checkstyle:javadocmethod")
    public void setUser(Set<User> userdetails) {
         this.userdetails = userdetails;
    }
    /**
     * Override method for ClientMetaData.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).
        append("clientID", clientID).
        append("clientCode", clientCode).
        append("clientName", clientName).
        append("url", url).
        append("logoURL", logoURL).
        toString();

    }

    /**
     * hashCode implementation of UserDTO.
     */
    @Override
    public int hashCode() {
          return new HashCodeBuilder()
                .append(clientID)
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
        ClientMetaData other = (ClientMetaData) obj;
              return new EqualsBuilder()
                .append(clientID, other.clientID)
                .isEquals();
     }
}
