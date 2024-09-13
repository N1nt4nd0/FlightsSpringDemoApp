package by.feodorkek.flights.service.impl;

import by.feodorkek.flights.dto.CityDto;
import by.feodorkek.flights.hibernate.types.LocalizedName;
import by.feodorkek.flights.model.Airport;
import by.feodorkek.flights.repository.AirportRepository;
import by.feodorkek.flights.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Page<LocalizedName> getAllCities(Pageable pageable) {
        return airportRepository.getAllCities(pageable);
    }

    @Transactional
    public Airport getByCode(String code) {
        return airportRepository.findById(code).orElseThrow(() -> new NoSuchElementException("Can't find airport by code"));
    }

    @Transactional
    public List<Airport> getByCity(CityDto cityDto) {
        return switch (cityDto.getCityNameLanguage().toLowerCase()) {
            case "ru" -> airportRepository.getByCityRu(cityDto.getCityName());
            case "en" -> airportRepository.getByCityEn(cityDto.getCityName());
            default -> throw new IllegalArgumentException("Incorrect city language name. Use 'en' or 'ru'");
        };
    }

}