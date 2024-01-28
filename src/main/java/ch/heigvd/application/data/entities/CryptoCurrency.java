package ch.heigvd.application.data.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

/**
 * This class is used to represent the crypto currency entity.
 *
 * @author Bijelic Alen & Bogale Tegest
 */
@Entity
@Table(name = "crypto_currency")
public class CryptoCurrency extends AbstractEntity {
    /**
     * The name of the crypto currency.
     * This field should not be null.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The symbol of the crypto currency.
     * This field should not be null.
     */
    @Column(name = "symbol", nullable = false)
    private String symbol;

    /**
     * The last price of the crypto currency.
     * This field should not be null
     */
    @Column(name = "last_price", nullable = false)
    private double lastPrice;

    /**
     * Set of trades for the crypto currency.
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "cryptoCurrency")
    private Set<Trade> trades;

    /**
     * Default Constructor for the crypto currency entity.
     * This constructor is needed for the JPA framework.
     */
    public CryptoCurrency() {
    }

    /**
     * Constructor for the crypto currency entity.
     *
     * @param name   The name
     * @param symbol The symbol
     */
    public CryptoCurrency(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    /**
     * Methode to get the name of the crypto currency.
     *
     * @return The name of the crypto currency
     */
    public String getName() {
        return name;
    }

    /**
     * Methode to set the name of the crypto currency.
     *
     * @param name The name of the crypto currency
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Methode to get the symbol of the crypto currency.
     *
     * @return The symbol of the crypto currency
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Methode to set the symbol of the crypto currency.
     *
     * @param symbol The symbol of the crypto currency
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Methode to get the last price of the crypto currency.
     *
     * @return The last price
     */
    public double getLastPrice() {
        return lastPrice;
    }

    /**
     * Methode to set the last price of the crypto currency.
     *
     * @param lastPrice The last price
     */
    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    /**
     * Methode to get the set of trades for the crypto currency.
     *
     * @return The set of trades
     */
    public Set<Trade> getTrades() {
        return trades;
    }

    /**
     * Methode to set the set of trades for the crypto currency.
     *
     * @param trades The set of trades
     */
    public void setTrades(Set<Trade> trades) {
        this.trades = trades;
    }

    /**
     * Methode to get the string representation of the crypto currency.
     *
     * @return The string representation of the crypto currency
     */
    public String toString() {
        return "CryptoCurrency{" + "name=" + name + ", symbol=" + symbol + ", lastPrice=" + lastPrice + '}';
    }
}
