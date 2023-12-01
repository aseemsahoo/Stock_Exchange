package com.project.stock_exchange.controller;

import com.project.stock_exchange.entity.DTO.UserSignupDTO;
import com.project.stock_exchange.entity.singleton.SessionID;
import com.project.stock_exchange.entity.User;
import com.project.stock_exchange.service.Interfaces.UserService;
import com.project.stock_exchange.service.Interfaces.UserSignupService;
import com.project.stock_exchange.util.ApiException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class LoginController
{
    @Autowired
    private final UserSignupService userSignupService;
    private final UserService userService;
    private SessionID sessionID;
    public LoginController(UserSignupService userSignupService, UserService userService, SessionID sessionID) {
        this.userSignupService = userSignupService;
        this.userService = userService;
        this.sessionID = sessionID;
    }

    @PostMapping("/signupSubmit")
    public ResponseEntity<?> signUp(@RequestBody Map<String, String> requestBody) throws Exception{
        String firstName = requestBody.get("firstName");
        String lastName = requestBody.get("lastName");
        String username = requestBody.get("username");
        String password = requestBody.get("password");

        UserSignupDTO userSignupDTO = new UserSignupDTO();

        userSignupDTO.setFirstName(firstName);
        userSignupDTO.setLastName(lastName);
        userSignupDTO.setEmail("N/A");
        userSignupDTO.setUsername(username);
        userSignupDTO.setPassword("{noop}" + userSignupDTO.getPassword());

        userSignupDTO.setEnabled(true);
        userSignupDTO.setBalance(BigDecimal.valueOf(10000.000));
        userSignupDTO.setInvested(BigDecimal.valueOf(0));

        try{
            userSignupService.saveUser(userSignupDTO);
        }
        catch(Exception ex){

            ApiException errorResponse = new ApiException(HttpStatus.FORBIDDEN, ex.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
        return ResponseEntity.ok("Success!");
    }

    @PostMapping("/loginSubmit")
    // change return type to something solid
    public ResponseEntity<?> login(@RequestBody Map<String, String> requestBody) throws Exception {
        String username = requestBody.get("username");
        String password = requestBody.get("password");

        password = "{noop}" + password;
        try{
            UserSignupDTO userSignupDTO = userSignupService.getUser(username, password);
            if(userSignupDTO == null) {
                throw new Exception("Invalid username or password");
            }
        }
        catch (Exception ex){
            ApiException errorResponse = new ApiException(HttpStatus.UNAUTHORIZED, ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
        User user = userService.getAccountDetails(username);
        sessionID.setUser(user);

        // singleton class set karo and others
        return ResponseEntity.ok(user);
    }
}
