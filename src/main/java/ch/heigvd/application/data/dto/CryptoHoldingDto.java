package ch.heigvd.application.data.dto;

import ch.heigvd.application.data.entities.CryptoCurrency;

/**
 * This class is used to represent the users crypto holdings.
 *
 * @author Alen Bijelic, Tegest Bogale
 */
public class CryptoHoldingDto {

    private CryptoCurrency cryptoCurrency;
    private Double quantity;

    /**
     * Constructor
     *
     * @param cryptoCurrency The CryptoCurrency
     * @param quantity       The quantity
     */
    public CryptoHoldingDto(CryptoCurrency cryptoCurrency, Double quantity) {
        this.cryptoCurrency = cryptoCurrency;
        this.quantity = quantity;
    }

    /**
     * Get the CryptoCurrency
     * @return The CryptoCurrency
     */
    public CryptoCurrency getCryptoCurrency() {
        return cryptoCurrency;
    }

    /**
     * Set the CryptoCurrency
     * @param cryptoCurrency The CryptoCurrency
     */
    public void setCryptoCurrency(CryptoCurrency cryptoCurrency) {
        this.cryptoCurrency = cryptoCurrency;
    }

    /**
     * Get the quantity
     * @return The quantity
     */
    public Double getQuantity() {
        return quantity;
    }

    /**
     * Set the quantity
     * @param quantity The quantity
     */
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
