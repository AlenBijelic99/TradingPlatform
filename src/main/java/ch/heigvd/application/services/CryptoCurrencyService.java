package ch.heigvd.application.services;

import ch.heigvd.application.data.entities.CryptoCurrency;
import ch.heigvd.application.data.repositories.CryptoCurrencyRepository;
import dev.hilla.BrowserCallable;
import dev.hilla.exception.EndpointException;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@BrowserCallable
@RolesAllowed("USER")
@Service
public class CryptoCurrencyService {
  private final CryptoCurrencyRepository cryptoCurrencyRepository;

  @Autowired
  public CryptoCurrencyService(CryptoCurrencyRepository cryptoCurrencyRepository) {
    this.cryptoCurrencyRepository = cryptoCurrencyRepository;
  }

  public List<CryptoCurrency> getAll() {
    return cryptoCurrencyRepository.findAll();
  }

  public List<CryptoCurrency> getAllWithPrice() {
    return cryptoCurrencyRepository.findAll();
  }

  public CryptoCurrency getOne(String symbol) throws RuntimeException {
    CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findBySymbol(symbol).orElse(null);
    if (cryptoCurrency == null) {
      throw new EndpointException("CryptoCurrency not found");
    }
    return cryptoCurrency;
  }
}
