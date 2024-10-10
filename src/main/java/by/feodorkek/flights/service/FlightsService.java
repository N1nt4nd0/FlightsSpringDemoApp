package by.feodorkek.flights.service;

import by.feodorkek.flights.dto.FlightScheduleDto;
import by.feodorkek.flights.hibernate.types.ContactData;
import by.feodorkek.flights.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FlightsService {

    /**
     * Getting flights list by passenger ticket number
     * <p>
     * Usage example:
     * <blockquote><pre>
     *     String ticketNumber = "0005432369020";
     *     List<Flight> flights = getByTicketNumber(ticketNumber);
     * </pre></blockquote>
     *
     * @param ticketNumber Ticket number input
     * @return Flights list by specified ticket number
     */
    List<Flight> getByTicketNumber(String ticketNumber);

    /**
     * Getting flights list by contact type (PHONE or EMAIL) and specified contact value
     * <p>
     * Getting by phone:
     * <blockquote><pre>
     * String phoneNumber = "+375295702036";
     * List<Flight> flights = getFlightsByContact(ContactData.Type.PHONE, phoneNumber);
     * </pre></blockquote>
     * Getting by email:
     * <blockquote><pre>
     * String email = "e.belova.07121974@postgrespro.ru";
     * List<Flight> flights = getFlightsByContact(ContactData.Type.EMAIL, email);
     * </pre></blockquote>
     *
     * @param contactType type of required contact (PHONE or EMAIL)
     * @param contact     specified contact value
     * @return flights list by specified contact values
     */
    List<Flight> getFlightsByContact(ContactData.Type contactType, String contact);

    /**
     * Returns a {@link Page} of flights by departure airport code,
     * arrival airport code, search start date, search end date, and
     * {@link Pageable} request object for using pagination for
     * building the {@link Page}
     * <p>
     * Code usage example:
     * <blockquote><pre>
     * FlightScheduleDto scheduleDto = FlightScheduleDto.builder()
     *      .departureAirportCode("VKO")
     *      .arrivalAirportCode("JOK")
     *      .startDate(LocalDate.now())
     *      .endDate(LocalDate.now().plusDays(1))
     *      .build();
     * PageRequest pageable = PageRequest.ofSize(10);
     * Page<Flight> schedulePage = getSchedule(scheduleDto, pageable);
     * </pre></blockquote>
     *
     * @param flightScheduleDto contains departure-arrival airport codes and start-end search dates
     * @param pageable          pageable object for using pagination
     * @return Page object which contains data based by pageable input object
     */
    Page<Flight> getSchedule(FlightScheduleDto flightScheduleDto, Pageable pageable);

}