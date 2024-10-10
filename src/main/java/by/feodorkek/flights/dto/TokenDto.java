package by.feodorkek.flights.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Token schema")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    @Schema(description = "Access token")
    private String accessToken;

    @Schema(description = "Token expiry in milliseconds")
    private long expiryTimeMs;

}