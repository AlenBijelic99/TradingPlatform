package ch.heigvd.application.services;

import ch.heigvd.application.data.entities.User;
import ch.heigvd.application.security.AuthenticatedUser;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * This class is used to get the authenticated user.
 *
 * @author Bijelic Alen & Bogale Tegest
 */
@Endpoint
@AnonymousAllowed
public class UserEndpoint {
    private final AuthenticatedUser authenticatedUser;

    /**
     * Constructor
     *
     * @param authenticatedUser The authenticated user
     */
    @Autowired
    public UserEndpoint(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    /**
     * This method is used to get the authenticated user.
     *
     * @return The authenticated user
     */
    public Optional<User> getAuthenticatedUser() {
        return authenticatedUser.get();
    }
}
