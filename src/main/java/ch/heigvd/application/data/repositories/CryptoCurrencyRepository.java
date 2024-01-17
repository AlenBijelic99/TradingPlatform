package ch.heigvd.application.data.repositories;

import ch.heigvd.application.data.entities.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


/**
 * This interface is used to define the methods to interact with the database for the CryptoCurrency entity.
 *
 * @author Alen Bijelic, Tegest Bogale
 */
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long>, JpaSpecificationExecutor<CryptoCurrency> {
  /**
   * This method is used to find a CryptoCurrency by its name.
   *
   * @param name The name of the CryptoCurrency.
   * @return The CryptoCurrency if found.
   */
  Optional<CryptoCurrency> findByName(String name);

  /**
   * This method is used to find a CryptoCurrency by its symbol.
   *
   * @param symbol The symbol of the CryptoCurrency.
   * @return The CryptoCurrency if found.
   */
  Optional<CryptoCurrency> findBySymbol(String symbol);
}
