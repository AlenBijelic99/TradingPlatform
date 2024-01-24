package ch.heigvd.application.services;

import ch.heigvd.application.data.entities.*;
import ch.heigvd.application.data.repositories.CryptoCurrencyRepository;
import ch.heigvd.application.data.repositories.PriceRepository;
import ch.heigvd.application.data.repositories.TradeRepository;
import ch.heigvd.application.security.AuthenticatedUser;
import dev.hilla.BrowserCallable;
import dev.hilla.exception.EndpointException;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The trade service
 * @author Alen Bijelic, Tegest Bogale
 */
@BrowserCallable
@RolesAllowed("USER")
@Service
public class TradeService {

    private final TradeRepository tradeRepository;
    private final AuthenticatedUser authenticatedUser;
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final PriceRepository priceRepository;

    /**
     * Constructor
     * @param tradeRepository The trade repository
     * @param authenticatedUser The authenticated user
     * @param cryptoCurrencyRepository The crypto currency repository
     * @param priceRepository The price repository
     */
    public TradeService(TradeRepository tradeRepository, AuthenticatedUser authenticatedUser, CryptoCurrencyRepository cryptoCurrencyRepository, PriceRepository priceRepository) {
        this.tradeRepository = tradeRepository;
        this.authenticatedUser = authenticatedUser;
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
        this.priceRepository = priceRepository;
    }

    /**
     * Buy a crypto currency
     * @param symbol The symbol of the crypto currency
     * @param quantity The quantity
     */
    @Transactional
    public void buy(String symbol, double quantity) {
        processTrade(symbol, quantity, TradeType.BUY);
    }

    /**
     * Sell a crypto currency
     * @param symbol The symbol of the crypto currency
     * @param quantity The quantity
     */
    @Transactional
    public void sell(String symbol, double quantity) {
        processTrade(symbol, quantity, TradeType.SELL);
    }

    /**
     * Process a trade
     * @param symbol The symbol of the crypto currency
     * @param quantity The quantity
     * @param tradeType The trade type
     */
    private void processTrade(String symbol, double quantity, TradeType tradeType) {
        User user = getUser();
        CryptoCurrency cryptoCurrency = getCryptoCurrency(symbol);
        Price price = getPrice(cryptoCurrency);

        double totalTradePrice = price.getPrice() * quantity;
        validateFunds(user, totalTradePrice, tradeType);

        updateFunds(user, totalTradePrice, tradeType);
        Trade trade = new Trade(user, price, quantity, tradeType);
        tradeRepository.save(trade);
    }

    /**
     * Get the user
     * @return The user
     */
    private User getUser() {
        return authenticatedUser.get()
                .orElseThrow(() -> new EndpointException("User not found"));
    }

    /**
     * Get the crypto currency
     * @param symbol The symbol of the crypto currency
     * @return The crypto currency
     */
    private CryptoCurrency getCryptoCurrency(String symbol) {
        return cryptoCurrencyRepository.findBySymbol(symbol)
                .orElseThrow(() -> new EndpointException("Crypto currency not found: " + symbol));
    }

    /**
     * Get the price of the crypto currency
     * @param cryptoCurrency The crypto currency
     * @return The price of the crypto currency
     */
    private Price getPrice(CryptoCurrency cryptoCurrency) {
        return priceRepository.findFirstByCryptoCurrencyOrderByDateDesc(cryptoCurrency)
                .orElseThrow(() -> new EndpointException("Price not found"));
    }

    /**
     * Validate the funds of the user
     * @param user The user
     * @param totalTradePrice The total trade price
     * @param tradeType The trade type
     */
    private void validateFunds(User user, double totalTradePrice, TradeType tradeType) {
        if (tradeType == TradeType.BUY && user.getFunds() - totalTradePrice < 0) {
            throw new EndpointException("Not enough funds");
        }
    }

    /**
     * Update the funds of the user
     * @param user The user
     * @param totalTradePrice The total trade price
     * @param tradeType The trade type
     */
    private void updateFunds(User user, double totalTradePrice, TradeType tradeType) {
        double newFunds = tradeType == TradeType.BUY ? user.getFunds() - totalTradePrice : user.getFunds() + totalTradePrice;
        user.setFunds(newFunds);
    }
}
