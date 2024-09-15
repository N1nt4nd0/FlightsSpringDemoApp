package by.feodorkek.flights.repository;

import by.feodorkek.flights.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface FlightsRepository extends JpaRepository<Flight, Integer> {

    @Query("""
            SELECT f FROM Flight f
            WHERE f.departureAirport = :departureAirportCode
            AND f.arrivalAirport = :arrivalAirportCode
            AND f.scheduledDeparture >= :startDateTime
            AND f.scheduledDeparture < :endDateTime
            """)
    Page<Flight> getSchedule(String departureAirportCode, String arrivalAirportCode, OffsetDateTime startDateTime, OffsetDateTime endDateTime, Pageable pageable);


    @Query("""
            SELECT f FROM Flight f
            JOIN TicketFlights tf
            ON f.id = tf.flightId
            WHERE tf.ticketNumber = :ticketNumber
            """)
    List<Flight> getByTicketNumber(String ticketNumber);

}