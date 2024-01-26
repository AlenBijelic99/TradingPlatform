package ch.heigvd.application.data.entities;

import jakarta.persistence.*;

/**
 * This class is used to represent a user of the application.
 *
 * @author Alen Bijelic, Tegest Bogale
 */
@Entity
@Table(name = "trade")
public class Trade extends AbstractEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "crypto_currency_id", nullable = false)
    private CryptoCurrency cryptoCurrency;

    @Column(name = "quantity", nullable = false)
    private double quantity;

    @Enumerated(EnumType.STRING)
    private TradeType type;

    /**
     * Default constructor
     */
    public Trade() {
    }

    /**
     * Constructor
     *
     * @param user     Trade user
     * @param price    Trade price
     * @param quantity Trade quantity
     */
    public Trade(User user, double price, CryptoCurrency cryptoCurrency, double quantity, TradeType type) {
        this.user = user;
        this.price = price;
        this.cryptoCurrency = cryptoCurrency;
        this.quantity = quantity;
        this.type = type;
    }

    /**
     * Get the user of the trade
     *
     * @return The user of the trade
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user of the trade
     *
     * @param user The user of the trade
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Get the price of the trade
     *
     * @return The price of the trade
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the price of the trade
     *
     * @param price The price of the trade
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get the crypto currency of the trade
     *
     * @return The crypto currency of the trade
     */
    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;

    }

    /**
     * Set the crypto currency of the trade
     *
     * @param cryptoCurrency The crypto currency of the trade
     */
    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    /**
     * Get the quantity of the trade
     *
     * @return The quantity of the trade
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Set the quantity of the trade
     *
     * @param quantity The quantity of the trade
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * Get the type of the trade
     *
     * @return The type of the trade
     */
    public TradeType getType() {
        return type;
    }

    /**
     * Set the type of the trade
     *
     * @param type The type of the trade
     */
    public void setType(TradeType type) {
        this.type = type;
    }

    /**
     * To string method
     *
     * @return
     */
    @Override
    public String toString() {
        return "Trade{" +
                "user=" + user +
                ", price=" + price +
                ", quantity=" + quantity +
                ", type=" + type +
                '}';
    }
}
