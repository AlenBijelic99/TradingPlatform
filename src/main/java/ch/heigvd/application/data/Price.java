package ch.heigvd.application.data;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * This class is used to represent a price of a crypto currency
 * @author Alen Bijelic, Tegest Bogale
 */
@Entity
@Table(name = "crypto_price")
public class Price extends AbstractEntity {
  @Column(name = "price")
  private Double price;

  @Column(name = "date")
  private Timestamp date;

  @ManyToOne
  @JoinColumn(name = "crypto_currency_id", nullable = false)
  private CryptoCurrency cryptoCurrency;

  /**
   * Create an empty price
   */
  public Price() {
  }

  /**
   * Create a price with the given price and crypto currency.
   * @param price The price of the crypto currency
   * @param cryptoCurrency The crypto currency
   */
  public Price(Double price, CryptoCurrency cryptoCurrency) {
    this.price = price;
    this.cryptoCurrency = cryptoCurrency;
  }

  /**
   * Return the price of the crypto currency.
   * @return The price of the crypto currency
   */
  public Double getPrice() {
    return price;
  }

  /**
   * Set the price of the crypto currency.
   * @param price The price of the crypto currency
   */
  public void setPrice(Double price) {
    price = price;
  }

  /**
   * Return the date of the price.
   * @return The date of the price
   */
  public Timestamp getDate() {
    return date;
  }

  /**
   * Set the date of the price.
   * @param date The date of the price
   */
  public void setTimestamp(Timestamp date) {
    this.date = date;
  }

  /**
   * Return the crypto currency of the price.
   * @return The crypto currency of the price
   */
  public CryptoCurrency getCryptoCurrency() {
    return cryptoCurrency;
  }

  /**
   * Set the crypto currency of the price.
   * @param cryptoCurrency The crypto currency of the price
   */
  public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
    this.cryptoCurrency = cryptoCurrency;
  }

  /**
   * Return a string representation of the price.
   * @return A string representation of the price
   */
  public String toString() {
    return "Price{" + "price=" + price + ", date=" + date + '}';
  }

  /**
   * Set the date of the price to the current date.
   */
  @PrePersist
  protected void onCreate() {
    date = Timestamp.from(Instant.now());
  }
}
