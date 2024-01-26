package ch.heigvd.application.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "crypto_currency")
public class CryptoCurrency extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "symbol", nullable = false)
    private String symbol;


    @Column(name = "last_price", nullable = false)
    private double lastPrice;

    @OneToMany(mappedBy = "cryptoCurrency")
    private Set<Trade> trades;

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

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Set<Trade> getTrades() {
        return trades;
    }

    public void setTrades(Set<Trade> trades) {
        this.trades = trades;
    }

    public String toString() {
        return "CryptoCurrency{" + "name=" + name + ", symbol=" + symbol + ", lastPrice=" + lastPrice + '}';
    }
}
