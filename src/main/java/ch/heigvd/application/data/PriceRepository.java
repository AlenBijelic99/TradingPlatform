package ch.heigvd.application.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * This interface is used to define the methods to interact with the database for the Price entity.
 * @author Alen Bijelic, Tegest Bogale
 */
public interface PriceRepository extends JpaRepository<CryptoCurrency, Long>, JpaSpecificationExecutor<CryptoCurrency> {
}
