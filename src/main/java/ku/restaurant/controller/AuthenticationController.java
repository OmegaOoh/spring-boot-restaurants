package ku.restaurant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ku.restaurant.dto.LoginRequest;
import ku.restaurant.dto.SignUpRequest;
import ku.restaurant.service.JwtUtils;
import ku.restaurant.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    
    @Autowired
    public AuthenticationController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtil;
    }
    
    @PostMapping("/login")
    public String authenticateUser(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails.getUsername());
    }
    
    @PostMapping("/signup")
    public String resgisterUser(@Valid @RequestBody SignUpRequest request) {
        if (userService.userExists(request.getUsername())) 
            return "Error: Username is already taken";
        
        userService.createUser(request);
        return "User registered successfully!S";
    }
}