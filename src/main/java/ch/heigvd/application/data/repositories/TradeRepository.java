package ch.heigvd.application.data.repositories;

import ch.heigvd.application.data.dto.CryptoHoldingDto;
import ch.heigvd.application.data.entities.CryptoCurrency;
import ch.heigvd.application.data.entities.Trade;
import ch.heigvd.application.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * This interface is used to define the methods to interact with the database for the Trade entity.
 *
 * @author Alen Bijelic, Tegest Bogale
 */
public interface TradeRepository extends JpaRepository<Trade, Long>, JpaSpecificationExecutor<Trade> {

    List<Trade> findAllByUserOrderByDateDesc(User user);

    @Query("SELECT SUM(CASE WHEN t.type = 'BUY' THEN t.quantity ELSE -t.quantity END) FROM Trade t WHERE t.user = ?1 AND t.cryptoCurrency = ?2")
    Optional<Double> findNetQuantityByUserAndCryptoCurrency(User user, CryptoCurrency cryptoCurrency);

    @Query("SELECT new ch.heigvd.application.data.dto.CryptoHoldingDto(t.cryptoCurrency, SUM(CASE WHEN t.type = 'BUY' THEN t.quantity ELSE -t.quantity END)) " +
            "FROM Trade t " +
            "WHERE t.user = :user " +
            "GROUP BY t.cryptoCurrency " +
            "HAVING SUM(CASE WHEN t.type = 'BUY' THEN t.quantity ELSE -t.quantity END) > 0")
    List<CryptoHoldingDto> findCryptoHoldingsByUser(@Param("user") User user);
}
