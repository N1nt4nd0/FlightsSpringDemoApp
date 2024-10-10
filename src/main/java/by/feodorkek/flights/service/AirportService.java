package by.feodorkek.flights.service;

import by.feodorkek.flights.dto.CityDto;
import by.feodorkek.flights.hibernate.types.LocalizedName;
import by.feodorkek.flights.model.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;

public interface AirportService {

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
    Page<LocalizedName> getAllCities(Pageable pageable);

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
    Airport getByCode(String code);

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
    List<Airport> getByCity(CityDto cityDto);

}