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
@Schema(description = "User schema")
public class FlightsUserDto {

    @Schema(description = "User name", example = "User")
    private String username;

    @Schema(description = "User password", example = "1234")
    private String password;

    @Schema(description = "User roles", example = "[\"USER\", \"ADMIN\"]")
    private String[] roles;

}