package ch.heigvd.application.services;

import ch.heigvd.application.data.dto.CryptoHoldingDto;
import ch.heigvd.application.data.entities.CryptoCurrency;
import ch.heigvd.application.data.entities.Trade;
import ch.heigvd.application.data.entities.TradeType;
import ch.heigvd.application.data.entities.User;
import ch.heigvd.application.data.repositories.CryptoCurrencyRepository;
import ch.heigvd.application.data.repositories.TradeRepository;
import ch.heigvd.application.security.AuthenticatedUser;
import dev.hilla.BrowserCallable;
import dev.hilla.exception.EndpointException;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * Constructor
     * @param tradeRepository The trade repository
     * @param authenticatedUser The authenticated user
     * @param cryptoCurrencyRepository The crypto currency repository
     */
    @Autowired
    public TradeService(TradeRepository tradeRepository, AuthenticatedUser authenticatedUser, CryptoCurrencyRepository cryptoCurrencyRepository) {
        this.tradeRepository = tradeRepository;
        this.authenticatedUser = authenticatedUser;
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
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

    public double getNetQuantity(String symbol) {
        User user = getUser();
        CryptoCurrency cryptoCurrency = getCryptoCurrency(symbol);
        return tradeRepository.findNetQuantityByUserAndCryptoCurrency(user, cryptoCurrency).orElse(0.0);
    }

    public List<Trade> getTrades() {
        User user = getUser();
        return tradeRepository.findAllByUserOrderByDateDesc(user);
    }

    public List<CryptoHoldingDto> getCryptoHoldings() {
        User user = getUser();
        return tradeRepository.findCryptoHoldingsByUser(user);
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

        if (tradeType == TradeType.BUY) {
            double totalTradePrice = cryptoCurrency.getLastPrice() * quantity;
            if (user.getFunds() - totalTradePrice < 0) {
                throw new EndpointException("Not enough funds");
            }
            updateFunds(user, totalTradePrice, tradeType);
        } else {
            double netQuantity = tradeRepository.findNetQuantityByUserAndCryptoCurrency(user, cryptoCurrency).orElse(0.0);
            if (netQuantity - quantity < 0) {
                throw new EndpointException("Not enough crypto currency");
            }
            updateFunds(user, cryptoCurrency.getLastPrice() * quantity, tradeType);
        }

        Trade trade = new Trade(user, cryptoCurrency.getLastPrice(), cryptoCurrency, quantity, tradeType);
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
