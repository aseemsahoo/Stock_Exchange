package com.project.stock_exchange.controller;

import com.project.stock_exchange.entity.SignupRequest;
import com.project.stock_exchange.entity.User;
import com.project.stock_exchange.entity.auth.AuthRequest;
//import com.project.stock_exchange.entity.dto.UserSignupDTO;
import com.project.stock_exchange.service.interfaces.UserService;
import com.project.stock_exchange.service.interfaces.UserSignupService;
import com.project.stock_exchange.util.exception.RestException;
import com.project.stock_exchange.util.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
public class LoginController
{
    @Autowired
    private final UserSignupService userSignupService;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JWTService jwtService;
    private final UserService userService;
    public LoginController(UserSignupService userSignupService, UserService userService, AuthenticationManager authManager, JWTService jwtService) {
        this.userSignupService = userSignupService;
        this.userService = userService;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/signupSubmit")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequestBody) throws Exception{

        User newUser = new User();

        newUser.setFirstName(signupRequestBody.getFirstName());
        newUser.setLastName(signupRequestBody.getLastName());

        // fix
//        newUser.setEmail("N/A");
        newUser.setEmail(signupRequestBody.getFirstName()+signupRequestBody.getLastName()+"@gmail.com");
        newUser.setUsername(signupRequestBody.getUsername());
        newUser.setPassword("{noop}" + signupRequestBody.getPassword());

        newUser.setRole("USER");
        newUser.setBalance(BigDecimal.valueOf(10000.000));
        newUser.setInvested(BigDecimal.valueOf(0));
        try{
            userSignupService.saveUser(newUser);
        }
        catch(DataIntegrityViolationException ex){
            throw ex;
        }
        return ResponseEntity.ok("Success!");
    }

    @PostMapping("/loginSubmit")
    // change return type to something solid
    public ResponseEntity<?> login(@RequestBody AuthRequest requestBody) throws Exception {
        String username = requestBody.getUsername();
        String password = requestBody.getPassword();

        try {
            UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(username, password);

            authManager.authenticate(authInputToken);

            String token = jwtService.generateToken(username);
            User user = userService.getAccountDetails(username);
            return ResponseEntity.ok(Collections.singletonMap("jwt-token", token));
        }
        catch (AuthenticationException authExc) {
            throw authExc;
        }
    }
}
