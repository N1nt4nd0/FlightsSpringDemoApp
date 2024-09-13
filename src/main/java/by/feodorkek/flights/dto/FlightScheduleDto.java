package by.feodorkek.flights.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Flights schedule schema")
public class FlightScheduleDto {

    @Schema(description = "Departure airport code", example = "VKO")
    private String departureAirportCode;

    @Schema(description = "Arrival airport code", example = "JOK")
    private String arrivalAirportCode;

    @Schema(description = "Start date", example = "2017-07-23")
    private LocalDate startDate;

    @Schema(description = "End date", example = "2017-07-25")
    private LocalDate endDate;

}