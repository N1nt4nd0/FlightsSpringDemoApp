package by.feodorkek.flights.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Arrays;
import java.util.UUID;

@Entity
@Table(name = "flights_users", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightsUser {

    public enum Roles {

        USER, ADMIN;

        public static String[] allRoles() {
            return Arrays.stream(values())
                    .map(String::valueOf)
                    .toList()
                    .toArray(new String[0]);
        }

    }

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String[] roles;

    @Column(name = "active")
    private boolean active;

}