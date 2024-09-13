package by.feodorkek.flights.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket_flights", schema = "bookings")
public class TicketFlights {

    @Id
    @Column(name = "ticket_no")
    @JdbcTypeCode(SqlTypes.CHAR)
    private String ticketNumber;

    @Column(name = "flight_id")
    private int flightId;

    @Column(name = "fare_conditions")
    private String fareConditions;

    @Column(name = "amount")
    private BigDecimal amount;

}