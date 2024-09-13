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

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.path}/auth")
@Tag(name = "Auth", description = "Authentication in flights API")
public class AuthRestController {

    private final AccessProvider accessProvider;
    private final AuthService authService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class))),
            @ApiResponse(responseCode = "500", description = "Exception on request",
                    content = @Content(mediaType = "text/plain", examples = @ExampleObject("Internal server error")))})
    @PostMapping("/login")
    @Operation(summary = "Login method")
    public ResponseEntity<?> login(@Parameter(description = "Login data for user authentication") @RequestBody LoginDto loginDto) {
        try {
            return ResponseEntity.ok(new TokenDto(authService.login(loginDto), authService.getTokenExpirationTime()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class))),
            @ApiResponse(responseCode = "500", description = "Exception on request",
                    content = @Content(mediaType = "text/plain", examples = {@ExampleObject("Internal server error"), @ExampleObject("Invalid input token")}))})
    @PostMapping("/refresh_token")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Refresh token request")
    public ResponseEntity<?> refreshToken(@Parameter(description = "Token data for refresh token") @RequestBody TokenDto tokenDto) {
        try {
            return ResponseEntity.ok(new TokenDto(authService.refreshToken(tokenDto), authService.getTokenExpirationTime()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check access to API",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CheckAccessDto.class))),
            @ApiResponse(responseCode = "500", description = "Exception on request",
                    content = @Content(mediaType = "text/plain", examples = @ExampleObject("Internal server error")))})
    @PostMapping("/check_access")
    @Operation(summary = "Check API availability")
    public ResponseEntity<CheckAccessDto> checkAccess() {
        return ResponseEntity.ok(new CheckAccessDto(accessProvider.checkAccess()));
    }

}