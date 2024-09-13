package by.feodorkek.flights.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "City schema")
public class CityDto {

    @Schema(description = "City name", example = "Moscow")
    private String cityName;

    @Schema(description = "City name language", example = "en")
    private String cityNameLanguage;

}