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

    @Query("""
            SELECT a.cityName
            FROM Airport a
            """)
    Page<LocalizedName> getAllCities(Pageable pageable);

    /**
     * Default in non-normalized database airports localized namings
     * stored in jsonb format with "ru" and "en" keys.
     * That's why search realized from specified native query
     *
     * @param cityRu city name in russian
     * @return airports list with naming matches
     */
    @Query(value = """
            SELECT *
            FROM bookings.airports_data
            where city ->> 'ru' = :cityRu
            """,
            nativeQuery = true)
    List<Airport> getByCityRu(String cityRu);

    /**
     * Default in non-normalized database airports localized namings
     * stored in jsonb format with "ru" and "en" keys.
     * That's why search realized from specified native query
     *
     * @param cityEn city name in english
     * @return airports list with naming matches
     */
    @Query(value = """
            SELECT *
            FROM bookings.airports_data
            where city ->> 'en' = :cityEn
            """,
            nativeQuery = true)
    List<Airport> getByCityEn(String cityEn);

}