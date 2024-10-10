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

@Entity
@Table(name = "seats", schema = "bookings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

    @Id
    @Column(name = "aircraft_code")
    @JdbcTypeCode(SqlTypes.CHAR)
    private String aircraftCode;

    @Column(name = "seat_no")
    private String seatNumber;

    @Column(name = "fare_conditions")
    private String fareConditions;

}