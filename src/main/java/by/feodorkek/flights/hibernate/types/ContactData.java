package by.feodorkek.flights.hibernate.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactData implements Serializable {

    public enum Type {

        PHONE, EMAIL;

    }

    @JsonProperty("phone")
    public String phone;

    @JsonProperty("email")
    public String email;

}