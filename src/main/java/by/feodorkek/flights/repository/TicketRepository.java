package by.feodorkek.flights.repository;

import by.feodorkek.flights.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String>, PagingAndSortingRepository<Ticket, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM bookings.tickets where contact_data ->> 'phone' = :phone")
    List<Ticket> getByPhone(String phone);

    @Query(nativeQuery = true, value = "SELECT * FROM bookings.tickets where contact_data ->> 'email' = :email")
    List<Ticket> getByEmail(String email);

}