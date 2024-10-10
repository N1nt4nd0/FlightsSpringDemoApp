package by.feodorkek.flights.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Check API access schema")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckAccessDto {

    @Schema(description = "Access API value", example = "false")
    private boolean access;

}
