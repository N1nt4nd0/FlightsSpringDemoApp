package by.feodorkek.flights.model;

import by.feodorkek.flights.hibernate.types.ContactData;
import by.feodorkek.flights.hibernate.types.ContactDataUserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "tickets", schema = "bookings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @Column(name = "ticket_no")
    @JdbcTypeCode(SqlTypes.CHAR)
    private String ticketNumber;

    @Column(name = "passenger_id")
    private String passengerId;

    @Column(name = "passenger_name")
    private String passengerName;

    @Column(name = "contact_data")
    @Type(ContactDataUserType.class)
    public ContactData contactData;

}