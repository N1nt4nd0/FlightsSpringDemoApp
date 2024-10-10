package by.feodorkek.flights.service.impl;

import by.feodorkek.flights.dto.FlightScheduleDto;
import by.feodorkek.flights.hibernate.types.ContactData;
import by.feodorkek.flights.model.Flight;
import by.feodorkek.flights.repository.FlightsRepository;
import by.feodorkek.flights.service.FlightsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link FlightsService} implementation for work with flights data
 */
@Service
@RequiredArgsConstructor
public class FlightsServiceImpl implements FlightsService {

    /**
     * {@link FlightsRepository} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     */
    private final FlightsRepository flightsRepository;

    /**
     * {@link TicketServiceImpl} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor} annotation
     */
    private final TicketServiceImpl ticketService;

    /**
     * Getting flights list by passenger ticket number
     * <p>
     * Usage example:
     * <blockquote><pre>
     *     String ticketNumber = "0005432369020";
     *     List flights = getByTicketNumber(ticketNumber);
     * </pre></blockquote>
     *
     * @param ticketNumber ticket number input
     * @return flights list by specified ticket number
     */
    @Override
    @Transactional
    public List<Flight> getByTicketNumber(String ticketNumber) {
        return flightsRepository.getByTicketNumber(ticketNumber);
    }

    /**
     * Getting flights list by contact type (PHONE or EMAIL) and specified contact value
     * <p>
     * Getting by phone:
     * <blockquote><pre>
     * String phoneNumber = "+375295702036";
     * List flights = getFlightsByContact(ContactData.Type.PHONE, phoneNumber);
     * </pre></blockquote>
     * Getting by email:
     * <blockquote><pre>
     * String email = "e.belova.07121974@postgrespro.ru";
     * List flights = getFlightsByContact(ContactData.Type.EMAIL, email);
     * </pre></blockquote>
     *
     * @param contactType type of required contact (PHONE or EMAIL)
     * @param contact     specified contact value
     * @return flights list by specified contact values
     */
    @Override
    @Transactional
    public List<Flight> getFlightsByContact(ContactData.Type contactType, String contact) {
        return ticketService.getTicketsByContact(contactType, contact).stream()
                .flatMap(ticket -> getByTicketNumber(ticket.getTicketNumber()).stream())
                .collect(Collectors.toList());
    }

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
     * Page schedulePage = getSchedule(scheduleDto, pageable);
     * </pre></blockquote>
     *
     * @param flightScheduleDto contains departure-arrival airport codes and start-end search dates
     * @param pageable          pageable object for using pagination
     * @return Page object which contains data based by pageable input object
     */
    @Override
    @Transactional
    public Page<Flight> getSchedule(FlightScheduleDto flightScheduleDto, Pageable pageable) {
        // Getting OffsetDateTimes from flightScheduleDto LocalDates for flightsRepository.getSchedule processing
        OffsetDateTime startDateTime = flightScheduleDto.getStartDate()
                // Start of day
                .atTime(LocalTime.MIDNIGHT)
                .atOffset(ZoneOffset.UTC);
        OffsetDateTime endDateTime = flightScheduleDto.getEndDate()
                // End of day
                .atTime(LocalTime.MAX)
                .atOffset(ZoneOffset.UTC);
        // Checking if end date time is after start date time
        if (endDateTime.isBefore(startDateTime)) {
            throw new IllegalArgumentException("Illegal date arguments. End date must be after start date");
        }
        // Airport codes to upper case
        String departureAirportCode = flightScheduleDto.getDepartureAirportCode().toUpperCase();
        String arrivalAirportCode = flightScheduleDto.getArrivalAirportCode().toUpperCase();
        return flightsRepository.getSchedule(departureAirportCode, arrivalAirportCode,
                startDateTime, endDateTime, pageable);
    }

}