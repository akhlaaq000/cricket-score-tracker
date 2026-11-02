package com.example.cricket.controller;

import com.example.cricket.model.User;
import com.example.cricket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOGGER = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String email = body.get("email");
            String password = body.get("password");

            // Validate input
            if (username == null || username.trim().isEmpty() || 
                email == null || email.trim().isEmpty() || 
                password == null || password.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Missing required fields"));
            }

            if (password.length() < 6) {
                return ResponseEntity.badRequest().body(Map.of("message", "Password must be at least 6 characters"));
            }

            Optional<User> existingUser = userRepository.findByUsername(username);
            if (existingUser.isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Username already exists"));
            }

            User user = new User();
            user.setUsername(username.trim());
            user.setEmail(email.trim());
            user.setPasswordHash(passwordEncoder.encode(password));
            userRepository.save(user);

            LOGGER.info("User registered successfully: " + username);
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        } catch (Exception e) {
            LOGGER.severe("Signup error: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of("message", "Registration failed"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String password = body.get("password");

            // Validate input
            if (username == null || username.trim().isEmpty() || 
                password == null || password.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Missing username or password"));
            }

            Optional<User> userOptional = userRepository.findByUsername(username);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password"));
            }

            User user = userOptional.get();
            if (!passwordEncoder.matches(password, user.getPasswordHash())) {
                return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password"));
            }

            String token = Jwts.builder()
                    .setSubject(user.getId().toString())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                    .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                    .compact();

            LOGGER.info("User logged in successfully: " + username);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            LOGGER.severe("Login error: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of("message", "Login failed"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie", "Authorization=; Max-Age=0; Path=/; HttpOnly");
            LOGGER.info("User logged out");
            return ResponseEntity.ok().headers(headers).body(Map.of("message", "Logged out successfully"));
        } catch (Exception e) {
            LOGGER.severe("Logout error: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of("message", "Logout failed"));
        }
    }
}
