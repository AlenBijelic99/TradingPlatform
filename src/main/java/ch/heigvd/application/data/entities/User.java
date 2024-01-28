package ch.heigvd.application.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

/**
 * This class is used to represent a user of the application.
 *
 * @author Alen Bijelic, Tegest Bogale
 */
@Entity
@Table(name = "application_user")
public class User extends AbstractEntity {

    @Column(name = "username", unique = true, nullable = false)
    @NotNull
    @NotBlank
    private String username;

    @Column(name = "firstname", length = 40)
    @NotNull
    @NotBlank
    private String firstname;

    @Column(name = "lastname", length = 40)
    @NotNull
    @NotBlank
    private String lastname;

    @Column(name = "hashed_password")
    @JsonIgnore
    @NotNull
    @NotBlank
    private String hashedPassword;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Column(name = "funds")
    private double funds;

    @OneToMany(mappedBy = "user")
    private Set<Trade> trades;

    public User() {
    }

    public User(String username, String firstname, String lastname, String hashedPassword, Set<Role> roles, double funds) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.hashedPassword = hashedPassword;
        this.roles = roles;
        this.funds = funds;
    }

    /**
     * Get the username of the user.
     *
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the user.
     *
     * @param username The username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the first name of the user.
     *
     * @return The first name of the user
     */
    public String getFirstName() {
        return firstname;
    }

    /**
     * Set the first name of the user.
     *
     * @param firstname The first name of the user
     */
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Get the last name of the user.
     *
     * @return The last name of the user
     */
    public String getLastName() {
        return lastname;
    }

    /**
     * Set the last name of the user.
     *
     * @param lastname The last name of the user
     */
    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Get the hashed password of the user.
     *
     * @return The hashed password of the user
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Set the hashed password of the user.
     *
     * @param hashedPassword The hashed password of the user
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    /**
     * Get the roles of the user.
     *
     * @return The roles of the user
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Set the roles of the user.
     *
     * @param roles The roles of the user
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Get the funds of the user.
     *
     * @return The funds of the user
     */
    public double getFunds() {
        return funds;
    }

    /**
     * Set the funds of the user.
     *
     * @param funds The funds of the user
     */
    public void setFunds(double funds) {
        this.funds = funds;
    }

    /**
     * To string method
     * @return The string representation of the user
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", roles=" + roles +
                ", funds=" + funds +
                '}';
    }
}
