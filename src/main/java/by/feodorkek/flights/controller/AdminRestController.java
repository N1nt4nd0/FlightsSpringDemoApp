package by.feodorkek.flights.controller;

import by.feodorkek.flights.config.AccessProvider;
import by.feodorkek.flights.dto.ActiveFlightsUserDto;
import by.feodorkek.flights.dto.FlightsUserDto;
import by.feodorkek.flights.service.FlightsUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * A {@link RestController @RestController} that implements functionality for users with the ADMIN role.
 * Implemented user management, configuration of service components, etc.
 */
@Tag(name = "Admin", description = "The admin access rest controller")
@RestController
@RequestMapping("${api.path}/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminRestController {

    /**
     * {@link FlightsUserService} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     * which serves for users management
     */
    private final FlightsUserService flightsUserService;

    /**
     * {@link AccessProvider} instance from Spring context
     * autowired by {@link RequiredArgsConstructor @RequiredArgsConstructor}
     * for setting up global users accesses to API endpoints
     */
    private final AccessProvider accessProvider;

    @Operation(summary = "Create new user")
    @PostMapping("/users/new")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully created",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject("User 'username' successfully created")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Exception on request",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject("Internal server error"),
                                    @ExampleObject("Bad arguments"),
                                    @ExampleObject("User already exists")
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content)
    })
    public ResponseEntity<?> createNewUser(
            @Parameter(description = "User data for creating new user")
            @RequestBody
            FlightsUserDto flightsUserDto) {
        try {
            flightsUserService.createUser(flightsUserDto);
            return ResponseEntity.ok(String.format("User '%s' successfully created", flightsUserDto.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Delete user by username")
    @DeleteMapping("/users/delete/{username}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully deleted",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject("User 'username' was deleted")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Exception on request",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject("Username 'username' not found"),
                                    @ExampleObject("Internal server error")
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content)
    })
    public ResponseEntity<?> deleteUser(
            @Parameter(
                    description = "Username for delete from database",
                    example = "Nolan"
            )
            @PathVariable
            String username) {
        try {
            flightsUserService.deleteUser(username);
            return ResponseEntity.ok(String.format("User '%s' was deleted", username));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(summary = "Set user active")
    @PatchMapping("/users/active")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User activity changed",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject("Activity for 'username' set to 'true'")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Exception on request",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject("Username 'username' not found"),
                                    @ExampleObject("Internal server error")
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content)
    })
    public ResponseEntity<?> setUserActive(
            @Parameter(description = "User data for change user activity")
            @RequestBody
            ActiveFlightsUserDto activeFlightsUserDto) {
        try {
            flightsUserService.setActive(activeFlightsUserDto);
            return ResponseEntity.ok(String.format("Activity for '%s' set to '%s'",
                    activeFlightsUserDto.getUsername(),
                    activeFlightsUserDto.isActive()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Operation(
            summary = "Close access to API for users",
            description = "Close to all access to API instead login requests and users with role 'ADMIN'"
    )
    @PostMapping("/users/close_access")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Access successfully closed",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject("Access closed")
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content
            )
    })
    public ResponseEntity<?> closeApiAccess() {
        accessProvider.closeAccess();
        return ResponseEntity.ok("Access closed");
    }

    @Operation(summary = "Open access to API for users")
    @PostMapping("/users/open_access")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Access successfully opened",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject("Access opened")
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content)
    })
    public ResponseEntity<String> openApiAccess() {
        accessProvider.openAccess();
        return ResponseEntity.ok("Access opened");
    }

}