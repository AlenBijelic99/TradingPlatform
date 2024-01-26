package ch.heigvd.application.services;

import ch.heigvd.application.data.entities.CryptoCurrency;
import ch.heigvd.application.data.repositories.CryptoCurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to get the price of a crypto currency from an external API.
 * @author Alen Bijelic, Tegest Bogale
 */
@Service
public class JmsPriceService {

  private final CryptoCurrencyRepository cryptoCurrencyRepository;

  private final JmsTemplate jmsTemplate;


  @Autowired
  public JmsPriceService(CryptoCurrencyRepository cryptoCurrencyRepository, JmsTemplate jmsTemplate) {
    this.cryptoCurrencyRepository = cryptoCurrencyRepository;
    this.jmsTemplate = jmsTemplate;
  }


  /**
   * Send the price of a crypto currency to a queue.
   * @param destination The name of the queue
   * @param cryptoCurrency The crypto currency
   * @param price The price of the crypto currency
   */
  public void sendPrice(String destination, CryptoCurrency cryptoCurrency, double price) {
    Map<String, Object> priceInfo = new HashMap<>();
    priceInfo.put("symbol", cryptoCurrency.getSymbol());
    priceInfo.put("price", price);
    jmsTemplate.convertAndSend(destination, priceInfo);
  }

  /**
   * Receive the price of a crypto currency from a queue.
   * @param priceInfo The price of the crypto currency
   */
  @JmsListener(destination = "cryptoPriceQueue")
  public void receivePrice(Map<String, Object> priceInfo) {
    // Retrieve price information
    String symbol = (String) priceInfo.get("symbol");
    double price = (Double) priceInfo.get("price");

    // Retrieve crypto currency from database
    CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findBySymbol(symbol).orElse(null);
    if (cryptoCurrency == null) {
      System.err.println("Crypto currency " + symbol + " doesn't exist in database");
      return;
    }

    // Save price
    cryptoCurrency.setLastPrice(price);
    cryptoCurrencyRepository.save(cryptoCurrency);
  }
}
