package by.feodorkek.flights.service;

import by.feodorkek.flights.dto.FlightScheduleDto;
import by.feodorkek.flights.hibernate.types.ContactData;
import by.feodorkek.flights.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FlightsService {

    List<Flight> getByTicketNumber(String ticketNumber);

    List<Flight> getFlightsByContact(ContactData.Type contactType, String contact);

    Page<Flight> getSchedule(FlightScheduleDto flightScheduleDto, Pageable pageable);

}