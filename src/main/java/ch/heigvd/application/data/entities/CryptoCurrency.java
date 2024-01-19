package ch.heigvd.application.data.entities;

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

    public CryptoCurrency() {
    }

    public CryptoCurrency(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Set<Price> getPrices() {
        return prices;
    }

    public void setPrices(Set<Price> prices) {
        this.prices = prices;
    }

    public String toString() {
        return "CryptoCurrency{" + "name=" + name + ", symbol=" + symbol + '}';
    }
}
