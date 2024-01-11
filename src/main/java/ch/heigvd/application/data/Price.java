package ch.heigvd.application.data;

import jakarta.persistence.*;

import java.sql.Timestamp;

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

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    price = price;
  }

  public Timestamp getDate() {
    return date;
  }

  public void setTimestamp(Timestamp date) {
    this.date = date;
  }

  public String toString() {
    return "Price{" + "price=" + price + ", date=" + date + '}';
  }
}
