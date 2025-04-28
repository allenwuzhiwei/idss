package com.nusiss.idss.controller;

import com.nusiss.idss.config.ApiResponse;
import com.nusiss.idss.dto.UserDTO;
import com.nusiss.idss.po.User;
import com.nusiss.idss.service.UserService;
import com.nusiss.idss.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtils jwtUtil;


    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserDTO>>> searchUsersByUsername(
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createDatetime") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<UserDTO> userPage = userService.searchByUsername(username, pageable);

        ApiResponse<Page<UserDTO>> response = new ApiResponse<>(
                true,
                "Users retrieved successfully",
                userPage
        );

        return ResponseEntity.ok(response);
    }

    /*@GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse<>(true, "Users retrieved successfully", users));
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Integer id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> ResponseEntity.ok(new ApiResponse<>(true, "User found", value)))
                .orElse(ResponseEntity.status(404).body(new ApiResponse<>(false, "User not found", null)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody UserDTO user) {

        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(new ApiResponse<>(true, "User created successfully", createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Integer id, @RequestBody UserDTO user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully", updatedUser));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(
            @RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        boolean isAuthenticated = userService.validateUserLogin(username, password);

        if (isAuthenticated) {
            //userService.updateLastLogin(username);
            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", token));
        } else {
            return ResponseEntity.status(401).body(new ApiResponse<>(false, "Invalid username or password", null));
        }
    }
}
