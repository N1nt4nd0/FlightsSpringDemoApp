package by.feodorkek.flights.service;

import by.feodorkek.flights.dto.CityDto;
import by.feodorkek.flights.hibernate.types.LocalizedName;
import by.feodorkek.flights.model.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirportService {

    Page<LocalizedName> getAllCities(Pageable pageable);

    Airport getByCode(String code);

    List<Airport> getByCity(CityDto cityDto);

}