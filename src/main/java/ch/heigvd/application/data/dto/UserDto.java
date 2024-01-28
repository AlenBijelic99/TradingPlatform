package ch.heigvd.application.data.dto;

import ch.heigvd.application.data.entities.User;

/**
 * This record is used to represent the user data transfer object.
 * @param username The username
 * @param password The password
 * @param firstName The first name
 * @param lastName The last name
 * @author Bijelic Alen & Bogale Tegest
 */
public record UserDto(
        String username,
        String password,
        String firstName,
        String lastName
) {
    /**
     * Constructor for the user data transfer object.
     * @param user The user
     * @return The user data transfer object
     */
    public static UserDto fromEntity(User user) {
        return new UserDto(
                user.getUsername(),
                user.getHashedPassword(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
