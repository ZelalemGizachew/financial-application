package com.zelalem.user.controller;

import com.zelalem.user.dto.request.CreateUserRequestDto;
import com.zelalem.user.service.UserService;
import com.zelalem.user.utils.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

/**Todo:
    API Endpoints:
        ○ POST /users: Create a new user and automatically provision a wallet for them.
            ■ Input: userId (unique string, e.g., email or username).
            ■ Output: userId, walletId (UUID of the newly created wallet). ---- User is expecting a response synchronously
        ○ GET /users/{userId}: Retrieve user details, including their associated walletId.
            ■ Output: userId, walletId. ---- User is expecting a response synchronously
*/

    @PostMapping("/")
    @Operation(summary = "Create User with Wallet", tags = "User", description = "Creates User with it's associated wallet")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success!", content = @Content(schema = @Schema(implementation = ResponseTemplate.class)))})
    public ResponseTemplate<?>  createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
        return userService.createUser(createUserRequestDto);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get User Details", tags = "User", description = "Get User Details by userId")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Success!", content = @Content(schema = @Schema(implementation = ResponseTemplate.class)))})
    public ResponseTemplate<?> getUserDetails(@PathVariable(name = "userId") String userId) {
        return userService.getUserDetails(userId);
    }
}
