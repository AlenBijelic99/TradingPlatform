package ch.heigvd.application.data.dto;

import ch.heigvd.application.data.entities.User;

public record UserDto(
        String username,
        String password,
        String firstName,
        String lastName
) {
    public static UserDto fromEntity(User user) {
        return new UserDto(
                user.getUsername(),
                user.getHashedPassword(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
