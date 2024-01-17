package ch.heigvd.application.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;

import java.util.Set;

/**
 * This class is used to represent a user of the application.
 * @author Alen Bijelic, Tegest Bogale
 */
@Entity
@Table(name = "application_user")
public class User extends AbstractEntity {

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "firstname", length = 40)
    private String firstname;

    @Column(name = "lastname", length = 40)
    private String lastname;

    @Column(name = "hashed_password")
    @JsonIgnore
    private String hashedPassword;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Column(name = "funds")
    private double funds;

    /**
     * Get the username of the user.
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the user.
     * @param username The username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the first name of the user.
     * @return The first name of the user
     */
    public String getFirstName() {
        return firstname;
    }

    /**
     * Set the first name of the user.
     * @param firstname The first name of the user
     */
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Get the last name of the user.
     * @return The last name of the user
     */
    public String getLastName() {
        return lastname;
    }

    /**
     * Set the last name of the user.
     * @param lastname The last name of the user
     */
    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Get the hashed password of the user.
     * @return The hashed password of the user
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Set the hashed password of the user.
     * @param hashedPassword The hashed password of the user
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    /**
     * Get the roles of the user.
     * @return The roles of the user
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Set the roles of the user.
     * @param roles The roles of the user
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Get the funds of the user.
     * @return The funds of the user
     */
    public double getFunds() {
        return funds;
    }

    /**
     * Set the funds of the user.
     * @param funds The funds of the user
     */
    public void setFunds(double funds) {
        this.funds = funds;
    }

}
