package by.feodorkek.flights.repository;

import by.feodorkek.flights.model.FlightsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightsUserRepository extends JpaRepository<FlightsUser, String> {
}