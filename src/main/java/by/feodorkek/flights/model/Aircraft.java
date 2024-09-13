package by.feodorkek.flights.model;

import by.feodorkek.flights.hibernate.types.LocalizedName;
import by.feodorkek.flights.hibernate.types.LocalizedNameUserType;
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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "aircrafts_data", schema = "bookings")
public class Aircraft {

    @Id
    @Column(name = "aircraft_code")
    @JdbcTypeCode(SqlTypes.CHAR)
    private String id;

    @Column(name = "model")
    @Type(LocalizedNameUserType.class)
    private LocalizedName aircraftModel;

    @Column(name = "range")
    private int range;

}