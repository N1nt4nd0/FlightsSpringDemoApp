package by.feodorkek.flights.model;

import by.feodorkek.flights.hibernate.types.LocalizedName;
import by.feodorkek.flights.hibernate.types.LocalizedNameUserType;
import by.feodorkek.flights.hibernate.types.PGPointUserType;
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
import org.postgresql.geometric.PGpoint;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "airports_data", schema = "bookings")
public class Airport {

    @Id
    @Column(name = "airport_code")
    @JdbcTypeCode(SqlTypes.CHAR)
    private String code;

    @Column(name = "airport_name")
    @Type(LocalizedNameUserType.class)
    private LocalizedName airportName;

    @Column(name = "city")
    @Type(LocalizedNameUserType.class)
    private LocalizedName cityName;

    @Column(name = "coordinates")
    @Type(PGPointUserType.class)
    private PGpoint point;

    @Column(name = "timezone")
    private String timeZone;

}