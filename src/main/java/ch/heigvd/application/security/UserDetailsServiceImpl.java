package ch.heigvd.application.security;

import ch.heigvd.application.data.entities.User;
import ch.heigvd.application.data.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is used to implement the UserDetailsService interface.
 * It is used to load the user by its username and to get the authorities of the user.
 *
 * @author Bijelic Alen & Bogale Tegest
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor
     *
     * @param userRepository The user repository
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * This method is used to load the user by its username.
     *
     * @param username The username of the user.
     * @return The user details.
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getHashedPassword(),
                    getAuthorities(user));
        }
    }

    /**
     * This method is used to get the authorities of the user.
     *
     * @param user The user.
     * @return The list of authorities.
     */

    private static List<GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

    }

}
