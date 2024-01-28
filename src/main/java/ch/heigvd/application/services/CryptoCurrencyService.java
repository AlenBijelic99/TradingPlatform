package ch.heigvd.application.services;

import ch.heigvd.application.data.entities.CryptoCurrency;
import ch.heigvd.application.data.repositories.CryptoCurrencyRepository;
import dev.hilla.BrowserCallable;
import dev.hilla.exception.EndpointException;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is used to implement the CryptoCurrencyService.
 * It is used to get all the crypto currencies and to get one crypto currency by its symbol.
 *
 * @author Bijelic Alen & Bogale Tegest
 */
@BrowserCallable
@RolesAllowed("USER")
@Service
public class CryptoCurrencyService {
    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    /**
     * Constructor
     *
     * @param cryptoCurrencyRepository The crypto currency repository
     */
    @Autowired
    public CryptoCurrencyService(CryptoCurrencyRepository cryptoCurrencyRepository) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
    }

    /**
     * This method is used to get all the crypto currencies.
     *
     * @return The list of crypto currencies.
     */
    public List<CryptoCurrency> getAll() {
        return cryptoCurrencyRepository.findAll();
    }

    /**
     * This method is used to get all the crypto currencies with their price.
     *
     * @return The list of crypto currencies.
     */
    public List<CryptoCurrency> getAllWithPrice() {
        return cryptoCurrencyRepository.findAllByOrderByIdAsc();
    }

    /**
     * This method is used to get one crypto currency by its symbol.
     *
     * @param symbol The symbol of the crypto currency.
     * @return The crypto currency.
     * @throws RuntimeException
     */
    public CryptoCurrency getOne(String symbol) throws RuntimeException {
        CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findBySymbol(symbol).orElse(null);
        if (cryptoCurrency == null) {
            System.err.println("Crypto currency " + symbol + " doesn't exist in database");
            throw new EndpointException("CryptoCurrency not found");
        }
        return cryptoCurrency;
    }
}
