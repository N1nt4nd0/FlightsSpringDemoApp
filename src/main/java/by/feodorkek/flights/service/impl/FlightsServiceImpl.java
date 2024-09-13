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

@Service
@RequiredArgsConstructor
public class FlightsServiceImpl implements FlightsService {

    private final FlightsRepository flightsRepository;
    private final TicketServiceImpl ticketService;

    @Transactional
    public List<Flight> getByTicketNumber(String ticketNumber) {
        return flightsRepository.getByTicketNumber(ticketNumber);
    }

    @Transactional
    public List<Flight> getFlightsByContact(ContactData.Type contactType, String contact) {
        return ticketService.getTicketsByContact(contactType, contact).stream()
                .flatMap(ticket -> getByTicketNumber(ticket.getTicketNumber()).stream())
                .collect(Collectors.toList());
    }

    @Transactional
    public Page<Flight> getSchedule(FlightScheduleDto flightScheduleDto, Pageable pageable) {
        OffsetDateTime startDateTime = flightScheduleDto.getStartDate().atTime(LocalTime.MIDNIGHT).atOffset(ZoneOffset.UTC);
        OffsetDateTime endDateTime = flightScheduleDto.getEndDate().atTime(LocalTime.MAX).atOffset(ZoneOffset.UTC);
        if (endDateTime.isBefore(startDateTime)) {
            throw new IllegalArgumentException("Illegal date arguments. End date must be after start date");
        }
        String departureAirportCode = flightScheduleDto.getDepartureAirportCode().toUpperCase();
        String arrivalAirportCode = flightScheduleDto.getArrivalAirportCode().toUpperCase();
        return flightsRepository.getSchedule(departureAirportCode, arrivalAirportCode, startDateTime, endDateTime, pageable);
    }

}