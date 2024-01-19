package ch.heigvd.application.data.repositories;


import ch.heigvd.application.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * This interface is used to define the methods to interact with the database for the User entity.
 * @author Alen Bijelic, Tegest Bogale
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * This method is used to find a User by its username.
     * @param username The username of the User.
     * @return The User if found.
     */
    User findByUsername(String username);
}
