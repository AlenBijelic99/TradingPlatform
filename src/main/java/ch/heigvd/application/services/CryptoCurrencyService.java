package ch.heigvd.application.services;

import ch.heigvd.application.data.CryptoCurrency;
import ch.heigvd.application.data.CryptoCurrencyRepository;
import dev.hilla.BrowserCallable;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@BrowserCallable
@RolesAllowed("USER")
@Service
public class CryptoCurrencyService {
    @Autowired
    private CryptoCurrencyRepository cryptoCurrencyRepository;

    public List<CryptoCurrency> getAll() {
        return cryptoCurrencyRepository.findAll();
    }

    public CryptoCurrency getCryptoWithLastPrice(String name) {
        return cryptoCurrencyRepository.getCryptoWithLastPrice(name);
    }
}
