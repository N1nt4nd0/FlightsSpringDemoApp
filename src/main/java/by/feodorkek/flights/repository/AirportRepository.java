package by.feodorkek.flights.repository;

import by.feodorkek.flights.hibernate.types.LocalizedName;
import by.feodorkek.flights.model.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String>, PagingAndSortingRepository<Airport, String> {

    @Query("SELECT a.cityName FROM Airport a")
    Page<LocalizedName> getAllCities(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM bookings.airports_data where city ->> 'ru' = :cityRu")
    List<Airport> getByCityRu(String cityRu);

    @Query(nativeQuery = true, value = "SELECT * FROM bookings.airports_data where city ->> 'en' = :cityEn")
    List<Airport> getByCityEn(String cityEn);

}