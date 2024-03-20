package com.fencing.midsouth.fmswebsite.controller;

import com.fencing.midsouth.fmswebsite.model.dto.AuthenticationResponse;
import com.fencing.midsouth.fmswebsite.model.dto.LoginDto;
import com.fencing.midsouth.fmswebsite.model.dto.SignUpDto;
import com.fencing.midsouth.fmswebsite.model.entity.Role;
import com.fencing.midsouth.fmswebsite.model.entity.User;
import com.fencing.midsouth.fmswebsite.repository.RoleRepository;
import com.fencing.midsouth.fmswebsite.repository.UserRepository;
import com.fencing.midsouth.fmswebsite.service.AuthService;
import com.fencing.midsouth.fmswebsite.service.CustomUserDetailsService;
import com.fencing.midsouth.fmswebsite.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public ResponseEntity<?> checkAuthenticated(@RequestHeader("Authorization") String bearerToken) {
        String token;
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
            String username = jwtService.extractUsername(token);
            if (username == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(username);
        }
        return ResponseEntity.status(401).build();
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        try {
            logger.info("POST /api/auth/login");
            logger.info("Username: {}, Password: {}", loginDto.getUsername(), loginDto.getPassword());

            authService.authenticate(
                    loginDto.getUsername(), loginDto.getPassword());

            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getUsername());

            String jwt = jwtService.generateToken(userDetails);

            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        } catch (BadCredentialsException badCredentialsException) {
            return new ResponseEntity<>("Username or password is incorrect", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpDto signUpDto) {
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_CLUB").get();
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
