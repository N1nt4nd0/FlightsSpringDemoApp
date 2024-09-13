package by.feodorkek.flights.controller;

import by.feodorkek.flights.hibernate.types.ContactData;
import by.feodorkek.flights.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER') and @accessProvider.checkAccess()")
@RequestMapping("${api.path}/tickets")
@Tag(name = "Tickets", description = "The tickets API")
public class TicketRestController {

    private final TicketService ticketService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of tickets by number returned",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject("[]"))),
            @ApiResponse(responseCode = "500", description = "Exception on request",
                    content = @Content(mediaType = "text/plain", examples = @ExampleObject("Internal server error"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @GetMapping("/phone/{number}")
    @Operation(summary = "Get tickets by phone number")
    public ResponseEntity<?> getTicketsByPhoneNumber(@Parameter(description = "Phone number for getting tickets list", example = "+70999705935") @PathVariable("number") String number) {
        try {
            return ResponseEntity.ok(ticketService.getTicketsByContact(ContactData.Type.PHONE, number));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of tickets by email returned",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject("[]"))),
            @ApiResponse(responseCode = "500", description = "Exception on request",
                    content = @Content(mediaType = "text/plain", examples = @ExampleObject("Internal server error"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @GetMapping("/email/{email}")
    @Operation(summary = "Get tickets by email")
    public ResponseEntity<?> getTicketsByEmail(@Parameter(description = "Email for getting tickets list", example = "e.belova.07121974@postgrespro.ru") @PathVariable("email") String email) {
        try {
            return ResponseEntity.ok(ticketService.getTicketsByContact(ContactData.Type.EMAIL, email));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}