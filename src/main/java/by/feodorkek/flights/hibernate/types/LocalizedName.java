package by.feodorkek.flights.hibernate.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Custom type for Hibernate which contents info about localized objects naming.
 * Used in cities and airports namings
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalizedName implements Serializable {

    @JsonProperty("en")
    private String en;

    @JsonProperty("ru")
    private String ru;

}