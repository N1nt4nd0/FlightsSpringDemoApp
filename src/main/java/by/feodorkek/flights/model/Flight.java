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

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flights", schema = "bookings")
public class Flight {

    public enum FlightStatus {

        SCHEDULED("Scheduled"), ON_TIME("On Time"), DELAYED("Delayed"), DEPARTED("Departed"), ARRIVED("Arrived"), CANCELLED("Cancelled");

        public final String statusName;

        FlightStatus(String statusName) {
            this.statusName = statusName;
        }

    }

    @Id
    @Column(name = "flight_id")
    private int id;

    @Column(name = "flight_no")
    @JdbcTypeCode(SqlTypes.CHAR)
    private String flightNo;

    @Column(name = "scheduled_departure")
    private OffsetDateTime scheduledDeparture;

    @Column(name = "scheduled_arrival")
    private OffsetDateTime scheduledArrival;

    @Column(name = "departure_airport")
    @JdbcTypeCode(SqlTypes.CHAR)
    private String departureAirport;

    @Column(name = "arrival_airport")
    @JdbcTypeCode(SqlTypes.CHAR)
    private String arrivalAirport;

    @Column(name = "status")
    private String status;

    @Column(name = "aircraft_code")
    @JdbcTypeCode(SqlTypes.CHAR)
    private String aircraftCode;

    @Column(name = "actual_departure")
    private OffsetDateTime actualDeparture;

    @Column(name = "actual_arrival")
    private OffsetDateTime actualArrival;

}