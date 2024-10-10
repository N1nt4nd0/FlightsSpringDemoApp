package by.feodorkek.flights.controller;

import by.feodorkek.flights.dto.FlightScheduleDto;
import by.feodorkek.flights.hibernate.types.ContactData;
import by.feodorkek.flights.model.Flight;
import by.feodorkek.flights.service.FlightsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A {@link RestController @RestController} that implements functionality for working with {@link FlightsService}
 */
@Tag(name = "Flights", description = "The flights API")
@RestController
@RequestMapping("${api.path}/flights")
@PreAuthorize("hasRole('USER') and @accessProvider.checkAccess()")
@RequiredArgsConstructor
public class FlightsRestController {

    /**
     * {@link FlightsService} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     */
    private final FlightsService flightsService;

    @Operation(summary = "Get schedule")
    @PostMapping("/schedule")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Page of schedule returned",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Exception on request",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject("Internal server error")
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content)
    })
    public ResponseEntity<?> getSchedule(
            @Parameter(description = "Schedule data for getting actual schedule")
            @RequestBody FlightScheduleDto flightScheduleDto,
            @Parameter(description = "Pageable data for getting schedule")
            Pageable pageable
    ) {
        try {
            Page<Flight> schedule = flightsService.getSchedule(flightScheduleDto, pageable);
            return ResponseEntity.ok(schedule);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Get flights by ticket number")
    @GetMapping("/ticket_number/{number}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of flights by ticket number returned",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("[]")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Exception on request",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject("Internal server error")
                    )
            ),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content)
    })
    public ResponseEntity<?> getFlightsByTicketNumber(
            @Parameter(
                    description = "Ticket number for getting flights list",
                    example = "0005432369020"
            )
            @PathVariable("number")
            String number) {
        try {
            List<Flight> byTicketNumber = flightsService.getByTicketNumber(number);
            return ResponseEntity.ok(byTicketNumber);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Get flights by phone number")
    @GetMapping("/phone/{number}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of flights by phone number returned",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("[]")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Exception on request",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject("Internal server error")
                    )
            ),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content)
    })
    public ResponseEntity<?> getFlightsByPhoneNumber(
            @Parameter(
                    description = "Phone number for getting flights list",
                    example = "+70999705935")
            @PathVariable("number")
            String number) {
        try {
            List<Flight> byContact = flightsService.getFlightsByContact(ContactData.Type.PHONE, number);
            return ResponseEntity.ok(byContact);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Get flights by email")
    @GetMapping("/email/{email}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of flights by email returned",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject("[]")
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Exception on request",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject("Internal server error")
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content)
    })
    public ResponseEntity<?> getFlightsByEmail(
            @Parameter(
                    description = "Email for getting flights list",
                    example = "e.belova.07121974@postgrespro.ru"
            )
            @PathVariable("email")
            String email) {
        try {
            List<Flight> byContact = flightsService.getFlightsByContact(ContactData.Type.EMAIL, email);
            return ResponseEntity.ok(byContact);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}