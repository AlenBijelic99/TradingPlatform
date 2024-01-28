package ch.heigvd.application.security;

import ch.heigvd.application.data.entities.User;
import ch.heigvd.application.data.repositories.UserRepository;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * This class is used to get the authenticated user.
 *
 * @author Bijelic Alen & Bogale Tegest
 */
@Component
public class AuthenticatedUser {

    private final UserRepository userRepository;
    private final AuthenticationContext authenticationContext;

    /**
     * Constructor
     *
     * @param authenticationContext The authentication context
     * @param userRepository        The user repository
     */
    public AuthenticatedUser(AuthenticationContext authenticationContext, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.authenticationContext = authenticationContext;
    }

    /**
     * This method is used to get the authenticated user.
     *
     * @return The authenticated user
     */
    @Transactional
    public Optional<User> get() {
        return authenticationContext.getAuthenticatedUser(Jwt.class)
                .map(userDetails -> userRepository.findByUsername(userDetails.getSubject()));
    }

    /**
     * This method is used to logout the user.
     * It will clear the authentication context.
     */
    public void logout() {
        authenticationContext.logout();
    }

}
