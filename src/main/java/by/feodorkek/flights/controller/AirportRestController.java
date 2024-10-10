package by.feodorkek.flights.controller;

import by.feodorkek.flights.dto.CityDto;
import by.feodorkek.flights.hibernate.types.LocalizedName;
import by.feodorkek.flights.model.Airport;
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

import java.util.List;

/**
 * A {@link RestController @RestController} that implements functionality with Airports service
 */
@Tag(name = "Airports", description = "The airports API")
@RestController
@RequestMapping("${api.path}/airports")
@PreAuthorize("hasRole('USER') and @accessProvider.checkAccess()")
@RequiredArgsConstructor
public class AirportRestController {

    /**
     * {@link AirportService} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     */
    private final AirportService airportService;

    @Operation(summary = "Get airports cities by pageable")
    @GetMapping("/cities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Page of cities returned",
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
    public ResponseEntity<?> getAllCities(
            @Parameter(description = "Pageable data for getting airports cities list")
            Pageable pageable) {
        try {
            Page<LocalizedName> allCities = airportService.getAllCities(pageable);
            return ResponseEntity.ok(allCities);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Get airports by city data")
    @PostMapping("/city")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of cities returned",
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
                            examples = {
                                    @ExampleObject("Incorrect city language name. Use 'en' or 'ru'"),
                                    @ExampleObject("Internal server error")
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content)
    })
    public ResponseEntity<?> getByCity(
            @Parameter(description = "City data for getting airports")
            @RequestBody
            CityDto cityDto) {
        try {
            List<Airport> byCity = airportService.getByCity(cityDto);
            return ResponseEntity.ok(byCity);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}