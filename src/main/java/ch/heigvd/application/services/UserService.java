package ch.heigvd.application.services;

import ch.heigvd.application.data.dto.UserDto;
import ch.heigvd.application.data.entities.Role;
import ch.heigvd.application.data.entities.User;
import ch.heigvd.application.data.repositories.UserRepository;
import java.util.Optional;
import java.util.Set;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.BrowserCallable;
import dev.hilla.Nullable;
import dev.hilla.crud.FormService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@BrowserCallable
@Service
public class UserService implements FormService<UserDto, Long> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> get(Long id) {
        return userRepository.findById(id);
    }

    public User update(User entity) {
        return userRepository.save(entity);
    }

    @AnonymousAllowed
    @Override
    public @Nullable UserDto save(UserDto value) {
        User existingUser = userRepository.findByUsername(value.username());
        if (existingUser != null) {
            throw new IllegalArgumentException("User already exists");
        }

        String hashedPassword = passwordEncoder.encode(value.password());

        User user = new User(
                value.username(),
                value.firstName(),
                value.lastName(),
                hashedPassword,
                Set.of(Role.USER),
                0
        );
        userRepository.save(user);
        return UserDto.fromEntity(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public Page<User> list(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> list(Pageable pageable, Specification<User> filter) {
        return userRepository.findAll(filter, pageable);
    }

    public int count() {
        return (int) userRepository.count();
    }

}
