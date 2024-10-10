package by.feodorkek.flights.controller;

import by.feodorkek.flights.config.AccessProvider;
import by.feodorkek.flights.dto.CheckAccessDto;
import by.feodorkek.flights.dto.LoginDto;
import by.feodorkek.flights.dto.TokenDto;
import by.feodorkek.flights.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A {@link RestController @RestController} with main authorization functionality for users
 */
@Tag(name = "Auth", description = "Authentication in flights API")
@RestController
@RequestMapping("${api.path}/auth")
@RequiredArgsConstructor
public class AuthRestController {

    /**
     * {@link AccessProvider} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     * which needs for checking API access
     */
    private final AccessProvider accessProvider;

    /**
     * {@link AuthService} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     * which required for main authorization functionality
     */
    private final AuthService authService;

    @Operation(summary = "Main login request")
    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Authentication success",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenDto.class)
                    )
            ),
            @ApiResponse(responseCode = "500",
                    description = "Exception on request",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject("Internal server error")
                    )
            )
    })
    public ResponseEntity<?> login(
            @Parameter(description = "Login data for user authentication")
            @RequestBody
            LoginDto loginDto) {
        try {
            TokenDto tokenDto = new TokenDto(authService.login(loginDto), authService.getTokenExpirationTime());
            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Refresh token request")
    @PostMapping("/refresh_token")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Token successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Exception on request",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject("Invalid input token"),
                                    @ExampleObject("Internal server error")
                            }
                    )
            )
    })
    public ResponseEntity<?> refreshToken(
            @Parameter(description = "Token data for refresh token")
            @RequestBody
            TokenDto tokenDto) {
        try {
            TokenDto refreshTokenDto = new TokenDto(authService.refreshToken(tokenDto),
                    authService.getTokenExpirationTime());
            return ResponseEntity.ok(refreshTokenDto);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Check API availability")
    @PostMapping("/check_access")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Check access to API",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CheckAccessDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Exception on request",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject("Internal server error")
                    )
            )
    })
    public ResponseEntity<CheckAccessDto> checkAccess() {
        CheckAccessDto checkAccessDto = new CheckAccessDto(accessProvider.checkAccess());
        return ResponseEntity.ok(checkAccessDto);
    }

}