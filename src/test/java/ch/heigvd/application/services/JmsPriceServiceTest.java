package ch.heigvd.application.services;

import ch.heigvd.application.data.entities.CryptoCurrency;
import ch.heigvd.application.data.entities.Price;
import ch.heigvd.application.data.repositories.CryptoCurrencyRepository;
import ch.heigvd.application.data.repositories.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class JmsPriceServiceTest {

  @Mock
  private PriceRepository priceRepository;

  @Mock
  private CryptoCurrencyRepository cryptoCurrencyRepository;

  @InjectMocks
  private JmsPriceService jmsPriceService;

  @BeforeEach
  public void setup() {
    openMocks(this);
  }

  @Test
  public void testReceivePriceAndAddToDatabase() {
    // Prepare the price information map
    Map<String, Object> priceInfo = new HashMap<>();
    priceInfo.put("name", "Bitcoin");
    priceInfo.put("symbol", "BTC");
    priceInfo.put("price", 50000.0);

    // Prepare a mock CryptoCurrency object
    CryptoCurrency bitcoin = new CryptoCurrency("Bitcoin", "BTC");
    when(cryptoCurrencyRepository.findBySymbol("BTC")).thenReturn(Optional.of(bitcoin));

    // Call the method to test
    jmsPriceService.receivePrice(priceInfo);

    // Verify that the save method of priceRepository is called
    verify(priceRepository).save(any(Price.class));
  }
}
