package by.feodorkek.flights.controller;

import by.feodorkek.flights.dto.FlightScheduleDto;
import by.feodorkek.flights.hibernate.types.ContactData;
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

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER') and @accessProvider.checkAccess()")
@RequestMapping("${api.path}/flights")
@Tag(name = "Flights", description = "The flights API")
public class FlightsRestController {

    private final FlightsService flightsService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page of schedule returned",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "500", description = "Exception on request",
                    content = @Content(mediaType = "text/plain", examples = @ExampleObject("Internal server error"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @PostMapping("/schedule")
    @Operation(summary = "Get schedule")
    public ResponseEntity<?> getSchedule(@Parameter(description = "Schedule data for getting actual schedule") @RequestBody FlightScheduleDto flightScheduleDto, Pageable pageable) {
        try {
            return ResponseEntity.ok(flightsService.getSchedule(flightScheduleDto, pageable));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of flights by ticket number returned",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject("[]"))),
            @ApiResponse(responseCode = "500", description = "Exception on request",
                    content = @Content(mediaType = "text/plain", examples = @ExampleObject("Internal server error"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @GetMapping("/ticket_number/{number}")
    @Operation(summary = "Get flights by ticket number")
    public ResponseEntity<?> getFlightsByTicketNumber(@Parameter(description = "Ticket number for getting flights list", example = "0005432369020") @PathVariable("number") String number) {
        try {
            return ResponseEntity.ok(flightsService.getByTicketNumber(number));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of flights by phone number returned",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject("[]"))),
            @ApiResponse(responseCode = "500", description = "Exception on request",
                    content = @Content(mediaType = "text/plain", examples = @ExampleObject("Internal server error"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @GetMapping("/phone/{number}")
    @Operation(summary = "Get flights by phone number")
    public ResponseEntity<?> getFlightsByPhoneNumber(@Parameter(description = "Phone number for getting flights list", example = "+70999705935") @PathVariable("number") String number) {
        try {
            return ResponseEntity.ok(flightsService.getFlightsByContact(ContactData.Type.PHONE, number));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of flights by email returned",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject("[]"))),
            @ApiResponse(responseCode = "500", description = "Exception on request",
                    content = @Content(mediaType = "text/plain", examples = @ExampleObject("Internal server error"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @GetMapping("/email/{email}")
    @Operation(summary = "Get flights by email")
    public ResponseEntity<?> getFlightsByEmail(@Parameter(description = "Email for getting flights list", example = "e.belova.07121974@postgrespro.ru") @PathVariable("email") String email) {
        try {
            return ResponseEntity.ok(flightsService.getFlightsByContact(ContactData.Type.EMAIL, email));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}