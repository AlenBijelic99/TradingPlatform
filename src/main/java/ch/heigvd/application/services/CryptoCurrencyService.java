package ch.heigvd.application.services;

import ch.heigvd.application.data.CryptoCurrency;
import ch.heigvd.application.data.CryptoCurrencyRepository;
import ch.heigvd.application.data.Price;
import ch.heigvd.application.data.PriceRepository;
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

    @Autowired
    private final PriceRepository priceRepository;

    public CryptoCurrencyService(CryptoCurrencyRepository cryptoCurrencyRepository, PriceRepository priceRepository) {
        this.cryptoCurrencyRepository = cryptoCurrencyRepository;
        this.priceRepository = priceRepository;
    }

    public record CryptoCurrencyRecord(
            String name,
            String symbol,
            double price
    ) {
    }

    private CryptoCurrencyRecord toCompanyRecord(CryptoCurrency cryptoCurrency) {
        Price lastPrice = priceRepository.findFirstByCryptoCurrencyOrderByDateDesc(cryptoCurrency).orElse(null);

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
