package by.feodorkek.flights.repository;

import by.feodorkek.flights.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String>, PagingAndSortingRepository<Ticket, String> {

    /**
     * Default in non-normalized database passenger contact data of ticket
     * stored in jsonb format with "phone" and "email" keys.
     * That's why search realized from specified native query
     *
     * @param phone phone number with code. E.g. +37595702036
     * @return tickets list with phone matches
     */
    @Query(value = """
            SELECT *
            FROM bookings.tickets
            where contact_data ->> 'phone' = :phone
            """,
            nativeQuery = true)
    List<Ticket> getByPhone(String phone);

    /**
     * Default in non-normalized database passenger contact data of ticket
     * stored in jsonb format with "phone" and "email" keys.
     * That's why search realized from specified native query
     *
     * @param email email of passenger
     * @return tickets list with email matches
     */
    @Query(value = """
            SELECT *
            FROM bookings.tickets
            where contact_data ->> 'email' = :email
            """,
            nativeQuery = true)
    List<Ticket> getByEmail(String email);

}