package by.feodorkek.flights.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", schema = "public")
public class FlightsUser {

    @Id
    @Column(name = "username", columnDefinition = "CITEXT")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String[] roles;

    @Column(name = "active")
    private boolean active;

}