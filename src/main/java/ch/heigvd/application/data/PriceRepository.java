package ch.heigvd.application.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * This interface is used to define the methods to interact with the database for the Price entity.
 * @author Alen Bijelic, Tegest Bogale
 */
public interface PriceRepository extends JpaRepository<Price, Long>, JpaSpecificationExecutor<Price> {
    Optional<Price> findFirstByCryptoCurrencyOrderByDateDesc(CryptoCurrency cryptoCurrency);
}
