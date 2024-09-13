package by.feodorkek.flights.hibernate.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalizedName implements Serializable {

    @JsonProperty("en")
    private String en;

    @JsonProperty("ru")
    private String ru;

}