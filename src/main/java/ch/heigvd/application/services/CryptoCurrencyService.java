package ch.heigvd.application.services;

import ch.heigvd.application.data.CryptoCurrency;
import ch.heigvd.application.data.CryptoCurrencyRepository;
import ch.heigvd.application.data.Price;
import dev.hilla.BrowserCallable;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@BrowserCallable
@RolesAllowed("USER")
@Service
public class CryptoCurrencyService {
    @Autowired
    private final CryptoCurrencyRepository cryptoCurrencyRepository;

    public CryptoCurrencyService(CryptoCurrencyRepository cryptoCurrencyRepository) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
    }

    public record CryptoCurrencyRecord(
            String name,
            String symbol,
            double price
    ) {
    }

    private CryptoCurrencyRecord toCompanyRecord(CryptoCurrency cryptoCurrency) {
        // TODO: Retrieve lastprice of a crypto currency with a request to be more eficient
        Price lastPrice = cryptoCurrency.getPrices().stream().max(Comparator.comparing(Price::getDate))
                .orElse(null);

        return new CryptoCurrencyRecord(
                cryptoCurrency.getName(),
                cryptoCurrency.getSymbol(),
                lastPrice != null ? lastPrice.getPrice() : 0
        );
    }

    public List<CryptoCurrency> getAll() {
        return cryptoCurrencyRepository.findAll();
    }

    public List<CryptoCurrencyRecord> getAllWithPrice() {
        return cryptoCurrencyRepository.findAll().stream()
                .map(this::toCompanyRecord).toList();
    }
}
