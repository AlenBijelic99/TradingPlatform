package ch.heigvd.application.services;

import ch.heigvd.application.data.entities.CryptoCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * This class is used to get the price of a crypto currency from an external API.
 * @author Alen Bijelic, Tegest Bogale
 */
@Service
public class CryptoPriceService {

  @Autowired
  private CryptoCurrencyService cryptoCurrencyService;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private JmsPriceService jmsPriceService;

  /**
   * This class represent the price data given by the API.
   */
  public static class PriceData {
    private String amount;
    private String base;
    private String currency;

    /**
     * Get the amount of the price.
     * @return The amount of the price
     */
    public String getAmount() {
      return amount;
    }

    /**
     * Set the amount of the price.
     * @param amount The amount of the price
     */
    public void setAmount(String amount) {
      this.amount = amount;
    }

    /**
     * Get the base of the price.
     * @return The base of the price
     */
    public String getBase() {
      return base;
    }

    /**
     * Set the base of the price.
     * @param base The base of the price
     */
    public void setBase(String base) {
      this.base = base;
    }

    /**
     * Get the currency of the price.
     * @return The currency of the price
     */
    public String getCurrency() {
      return currency;
    }

    /**
     * Set the currency of the price.
     * @param currency The currency of the price
     */
    public void setCurrency(String currency) {
      this.currency = currency;
    }
  }

  /**
   * This class represent the response given by the API.
   */
  public static class ApiResponse {
    private PriceData data;

    /**
     * Get the price data.
     * @return The price data
     */
    public PriceData getData() {
      return data;
    }

    /**
     * Set the price data.
     * @param data The price data
     */
    public void setData(PriceData data) {
      this.data = data;
    }
  }

  /**
   * Fetch the price of all crypto currencies every 10 seconds.
   */
  @Scheduled(fixedRate = 10000)
  public void schedulePriceFetch() {
    for (CryptoCurrency cryptoCurrency : cryptoCurrencyService.getAll()) {
      fetchPrice(cryptoCurrency);
    }
  }

  /**
   * Fetch the price of a crypto currency.
   * @param cryptoCurrency The crypto currency
   */
  public void fetchPrice(CryptoCurrency cryptoCurrency) {
    String url = "https://api.coinbase.com/v2/prices/" + cryptoCurrency.getSymbol() + "-USD/spot";
    ResponseEntity<ApiResponse> response = restTemplate.getForEntity(url, ApiResponse.class);

    ApiResponse apiResponse = response.getBody();
    if (apiResponse != null) {
      PriceData priceData = apiResponse.getData();
      double priceAmmount = Double.parseDouble(priceData.getAmount());
      jmsPriceService.sendPrice("cryptoPriceQueue", cryptoCurrency, priceAmmount);
    }
  }
}
