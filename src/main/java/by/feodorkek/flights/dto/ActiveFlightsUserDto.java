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
@Schema(description = "Activity user schema")
public class ActiveFlightsUserDto {

    @Schema(description = "Username", example = "User")
    private String username;

    @Schema(description = "Activity boolean value", example = "true/false")
    private boolean active;

}