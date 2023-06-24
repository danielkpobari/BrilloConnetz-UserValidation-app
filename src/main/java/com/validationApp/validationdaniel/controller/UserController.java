package com.validationApp.validationdaniel.controller;

import com.validationApp.validationdaniel.model.UserFields;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
public class UserController {
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{7,}$");



    @PostMapping("/validate")
    public ResponseEntity<?> validateFields(@RequestBody UserFields userFields) {
        Map<String, String> errors = new HashMap<>();

        if (userFields.getUsername().isEmpty() || userFields.getUsername().length() < 4) {
            errors.put("Username", "Username should not be empty and should have a minimum of 4 characters");
        }

        if (userFields.getEmail().isEmpty() || !EMAIL_REGEX.matcher(userFields.getEmail()).matches()) {
            errors.put("Email", "Email should not be empty and should be a valid email address");
        }

        if (userFields.getPassword().isEmpty() || !PASSWORD_REGEX.matcher(userFields.getPassword()).matches()) {
            errors.put("Password", "Password should not be empty and should be a strong password");
        }

        if (userFields.getDateOfBirth() == null || !isMinimumAgeReached(userFields.getDateOfBirth())) {
            errors.put("Date of Birth", "Date of Birth should not be empty and should be 16 years or greater");
        }

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        // All validations passed, generate and return a JWT
        String token = generateJWT(userFields.getUsername());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestBody String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey("secrejicjowowncnocnwoiciwoniIIKKEknjdjdnkjcdkjnckjjntkey") // Replace with your secret key
                    .parseClaimsJws(token);

            return ResponseEntity.ok("Verification pass");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Verification fails");
        }
    }

    private boolean isMinimumAgeReached(LocalDate dateOfBirth) {
//        String dateString = "2023-06-23"; // Example date string in yyyy-MM-dd format
//
//        // Define the desired date format
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        try {
//            // Parse the string to LocalDate
//            LocalDate date = LocalDate.parse(dateString, formatter);
//
//            // Print the LocalDate
//            System.out.println(date);
//        } catch (DateTimeParseException e) {
//            System.out.println("Invalid date format: " + dateString);
//            // Handle the parsing error as needed
//        }
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(dateOfBirth, currentDate);
        int minimumAge = 16; // Minimum age requirement

        return age.getYears() >= minimumAge;

        // Perform the age calculation logic here
       // return true; // Replace with your implementation
    }

    private String generateJWT(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 86400000); // Set token expiration to 24 hours

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, "secrejicjowowncnocnwoiciwoniIIKKEknjdjdnkjcdkjnckjjntkey") // Replace with your secret key
                .compact();

        return token;
    }
}
