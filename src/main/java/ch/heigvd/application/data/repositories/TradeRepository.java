package ch.heigvd.application.data.repositories;

import ch.heigvd.application.data.entities.Trade;
import ch.heigvd.application.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * This interface is used to define the methods to interact with the database for the Trade entity.
 * @author Alen Bijelic, Tegest Bogale
 */
public interface TradeRepository extends JpaRepository<Trade, Long>, JpaSpecificationExecutor<Trade> {
    Optional<List<Trade>> findAllByUser(User user);
}
