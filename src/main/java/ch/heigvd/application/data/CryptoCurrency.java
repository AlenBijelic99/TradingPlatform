package ch.heigvd.application.data;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "crypto_currency")
public class CryptoCurrency extends AbstractEntity {
  private String name;

  private String symbol;

  @OneToMany(mappedBy = "cryptoCurrency")
  private Set<Price> prices;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    name = name;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    symbol = symbol;
  }

  public String toString() {
    return "CryptoCurrency{" + "name=" + name + ", symbol=" + symbol + '}';
  }
}