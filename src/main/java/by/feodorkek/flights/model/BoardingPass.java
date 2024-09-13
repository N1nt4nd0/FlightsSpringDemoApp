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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "boarding_passes", schema = "bookings")
public class BoardingPass {

    @Id
    @Column(name = "ticket_no")
    @JdbcTypeCode(SqlTypes.CHAR)
    private String ticketNumber;

    @Column(name = "flight_id")
    private int flightId;

    @Column(name = "boarding_no")
    private int boardingNumber;

    @Column(name = "seat_no")
    private String seatNumber;

}