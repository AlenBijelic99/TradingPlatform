package ch.heigvd.application.data;

import ch.heigvd.application.data.entities.CryptoCurrency;
import ch.heigvd.application.data.repositories.CryptoCurrencyRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class is used to test the PriceRepository class
 *
 * @author Alen Bijelic, Tegest Bogale
 */
@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PriceRepositoryTest {

    @Autowired
    private CryptoCurrencyRepository cryptoCurrencyRepository;

    private CryptoCurrency cryptoCurrency;

    @BeforeAll
    public void init() {
        cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setName("Bitcoin");
        cryptoCurrency.setSymbol("BTC");
        cryptoCurrencyRepository.save(cryptoCurrency);
    }

    /**
     * Test if the price is saved correctly.
     */
    @Test
    public void testSavePrice() {
        // Save price
        cryptoCurrency.setLastPrice(10000);
        cryptoCurrencyRepository.save(cryptoCurrency);

        // Retrieve price
        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findBySymbol("BTC").orElse(null);
        assertNotNull(cryptoCurrency);
        assertEquals(10000, cryptoCurrency.getLastPrice());
    }
}
