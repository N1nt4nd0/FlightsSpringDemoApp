package by.feodorkek.flights.service;

import by.feodorkek.flights.dto.ActiveFlightsUserDto;
import by.feodorkek.flights.dto.FlightsUserDto;
import by.feodorkek.flights.model.FlightsUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface FlightsUserService {

    /**
     * Getting {@link Page} of users in service using pagination request in input parameter
     * <p>
     * Example of code usage for getting all users in database:
     * <blockquote><pre>
     * PageRequest pageable = PageRequest.ofSize(Integer.MAX_VALUE);
     * Page<FlightUser> flightUsers = getAllUsers(pageable);
     * </pre></blockquote>
     *
     * @param pageable pageable object for using pagination
     * @return {@link Page} object which contains data based by pageable input object
     */
    Page<FlightsUser> getAllUsers(Pageable pageable);

    /**
     * Getting {@link FlightsUser} by username
     *
     * @param username name of user
     * @return {@link FlightsUser} object
     * @throws UsernameNotFoundException if user by specified username not found
     */
    FlightsUser getByUsername(String username);

    /**
     * Deleting {@link FlightsUser} by username
     *
     * @param username name of user
     * @throws UsernameNotFoundException if user by specified username not found
     */
    void deleteUser(String username);

    /**
     * Creating new user by username, password, which roles contains in {@link FlightsUserDto}
     *
     * @param flightsUserDto username, password, roles Dto parameter
     */
    void createUser(FlightsUserDto flightsUserDto);

    /**
     * Set {@link FlightsUser} active or inactive
     *
     * @param activeFlightsUserDto contains username and user activity boolean value
     * @throws UsernameNotFoundException if user not found
     */
    void setActive(ActiveFlightsUserDto activeFlightsUserDto);

}