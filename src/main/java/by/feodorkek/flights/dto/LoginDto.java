package by.feodorkek.flights.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Login user schema")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @Schema(description = "Username", example = "admin")
    private String username;

    @Schema(description = "Password", example = "admin")
    private String password;

}