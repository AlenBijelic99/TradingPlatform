package ch.heigvd.application.data;

import jakarta.annotation.PostConstruct;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // To be able to use @BeforeAll non-static and call cryptoCurrencyRepository
public class PriceRepositoryTest {
    @Autowired
    private PriceRepository priceRepository;

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
        Price price = new Price(100.0, cryptoCurrency);
        priceRepository.save(price);

        Price retrievedPrice = priceRepository.findById(price.getId()).orElse(null);
        assertNotNull(retrievedPrice);
        assertNotNull(retrievedPrice.getPrice());
        assertNotNull(retrievedPrice.getDate());
        assertEquals(retrievedPrice.getPrice(), price.getPrice());
    }
}
