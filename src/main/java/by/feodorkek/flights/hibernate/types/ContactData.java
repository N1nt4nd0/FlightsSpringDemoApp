package by.feodorkek.flights.hibernate.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Custom type for Hibernate which contents info about person contact.
 * Used in tickets booking
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactData implements Serializable {

    public enum Type {

        PHONE, EMAIL;

    }

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

}