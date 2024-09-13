package by.feodorkek.flights.controller;

import by.feodorkek.flights.dto.CityDto;
import by.feodorkek.flights.service.AirportService;
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
@RequestMapping("${api.path}/airports")
@Tag(name = "Airports", description = "The airports API")
public class AirportRestController {

    private final AirportService airportService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page of cities returned",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "500", description = "Exception on request",
                    content = @Content(mediaType = "text/plain", examples = @ExampleObject("Internal server error"))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @GetMapping("/cities")
    @Operation(summary = "Get airports cities by pageable")
    public ResponseEntity<?> getAllCities(@Parameter(description = "Pageable data for getting airports cities list") Pageable pageable) {
        try {
            return ResponseEntity.ok(airportService.getAllCities(pageable));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of cities returned",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject("[]"))),
            @ApiResponse(responseCode = "500", description = "Exception on request",
                    content = @Content(mediaType = "text/plain", examples = {@ExampleObject("Internal server error"), @ExampleObject("Incorrect city language name. Use 'en' or 'ru'")})),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)})
    @PostMapping("/city")
    @Operation(summary = "Get airports by city data")
    public ResponseEntity<?> getByCity(@Parameter(description = "City data for getting airports") @RequestBody CityDto cityDto) {
        try {
            return ResponseEntity.ok(airportService.getByCity(cityDto));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}