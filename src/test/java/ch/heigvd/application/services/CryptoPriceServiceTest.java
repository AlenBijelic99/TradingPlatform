package ch.heigvd.application.services;

import ch.heigvd.application.data.entities.CryptoCurrency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class CryptoPriceServiceTest {

  @Mock
  private CryptoCurrencyService cryptoCurrencyService;

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private JmsPriceService jmsPriceService;

  @InjectMocks
  private PriceFetchService priceFetchService;

  @BeforeEach
  public void setup() {
    openMocks(this);
  }

  @Test
  public void testFetchPrice() {
    // Create crypto currencies
    CryptoCurrency bitcoin = new CryptoCurrency("Bitcoin", "BTC");
    CryptoCurrency ethereum = new CryptoCurrency("Ethereum", "ETH");

    // Mock cryptoCurrencyService.getAll() to return a list of crypto currencies
    when(cryptoCurrencyService.getAll()).thenReturn(Arrays.asList(bitcoin, ethereum));

    // Create an API-like response with a price
    PriceFetchService.ApiResponse apiResponse = new PriceFetchService.ApiResponse();
    PriceFetchService.PriceData priceData = new PriceFetchService.PriceData();
    priceData.setAmount("50000");
    priceData.setBase("BTC");
    priceData.setCurrency("USD");
    apiResponse.setData(priceData);

    // Mock restTemplate.getForEntity() to return a response with a price
    when(restTemplate.getForEntity(anyString(), eq(PriceFetchService.ApiResponse.class)))
            .thenReturn(ResponseEntity.ok(apiResponse));

    // Call the method to test
    priceFetchService.schedulePriceFetch();

    // Verify that the sendPrice() method of jmsPriceService is called twice
    verify(jmsPriceService, times(2)).sendPrice(eq("cryptoPriceQueue"), any(CryptoCurrency.class), anyDouble());
  }
}
