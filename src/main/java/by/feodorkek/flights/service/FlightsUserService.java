package by.feodorkek.flights.service;

import by.feodorkek.flights.dto.ActiveFlightsUserDto;
import by.feodorkek.flights.dto.FlightsUserDto;
import by.feodorkek.flights.model.FlightsUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightsUserService {

    Page<FlightsUser> getAllUsers(Pageable pageable);

    FlightsUser getByUsername(String username);

    void deleteUser(String username);

    void createUser(FlightsUserDto flightsUserDto);

    void setActive(ActiveFlightsUserDto activeFlightsUserDto);

}