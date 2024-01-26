package ch.heigvd.application.services;

import ch.heigvd.application.data.entities.User;
import ch.heigvd.application.security.AuthenticatedUser;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Endpoint
@AnonymousAllowed
public class UserEndpoint {
    private final AuthenticatedUser authenticatedUser;

    @Autowired
    public UserEndpoint(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    public Optional<User> getAuthenticatedUser() {
        return authenticatedUser.get();
    }
}
