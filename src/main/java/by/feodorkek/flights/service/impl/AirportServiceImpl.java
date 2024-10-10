package by.feodorkek.flights.service.impl;

import by.feodorkek.flights.dto.CityDto;
import by.feodorkek.flights.hibernate.types.LocalizedName;
import by.feodorkek.flights.model.Airport;
import by.feodorkek.flights.repository.AirportRepository;
import by.feodorkek.flights.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * {@link AirportService} implementation for work with airports data
 */
@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    /**
     * {@link AirportRepository} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     */
    private final AirportRepository airportRepository;

    /**
     * Getting {@link Page} of {@link LocalizedName} airport cities namings
     * using pagination request in input parameter
     * <p>
     * Example of code usage for getting all airport cities in database:
     * <blockquote><pre>
     * PageRequest pageable = PageRequest.ofSize(Integer.MAX_VALUE);
     * Page localizedCitiesNamesPage = getAllCities(pageable);
     * </pre></blockquote>
     *
     * @param pageable pageable object for using pagination
     * @return {@link Page} object which contains data based by pageable input object
     */
    @Transactional
    public Page<LocalizedName> getAllCities(Pageable pageable) {
        return airportRepository.getAllCities(pageable);
    }

    /**
     * Returns {@link Airport} entity object by airport code
     * <p>
     * Usage example:
     * <blockquote><pre>
     * Airport airport = getByCode("VKO");
     * </pre></blockquote>
     *
     * @param code airport code, e.g. "VKO", "JOK"
     * @return airport object by specified code
     * @throws NoSuchElementException if airport not found by code
     */
    @Transactional
    public Airport getByCode(String code) {
        return airportRepository.findById(code.toUpperCase())
                .orElseThrow(() ->
                        new NoSuchElementException("Can't find airport by code")
                );
    }

    /**
     * Return list of airports by localized name and parameter
     * <p>
     * Usage example:
     * <blockquote><pre>
     * CityDto cityDto = new CityDto("Moscow", "en");
     * List airports = getByCity(cityDto);
     * </pre></blockquote>
     *
     * @param cityDto dto with city name and language code, e.g. 'en', 'ru'
     * @return airports list by specified {@link CityDto}
     */
    @Transactional
    public List<Airport> getByCity(CityDto cityDto) {
        return switch (cityDto.getCityNameLanguage().toLowerCase()) {
            case "ru" -> airportRepository.getByCityRu(cityDto.getCityName());
            case "en" -> airportRepository.getByCityEn(cityDto.getCityName());
            default -> throw new IllegalArgumentException("Incorrect city language name. Use 'en' or 'ru'");
        };
    }

}