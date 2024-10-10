package by.feodorkek.flights.repository;

import by.feodorkek.flights.model.FlightsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlightsUserRepository extends JpaRepository<FlightsUser, UUID> {

    /**
     * JPA Query method which ignore case of input username string
     *
     * @param username username of flights database user
     * @return {@link Optional} of {@link FlightsUser}
     */
    Optional<FlightsUser> findByUsernameIgnoreCase(String username);

}