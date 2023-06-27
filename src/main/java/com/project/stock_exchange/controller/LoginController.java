package com.project.stock_exchange.controller;

import com.project.stock_exchange.entity.DTO.UserSignupDTO;
import com.project.stock_exchange.entity.singleton.SessionID;
import com.project.stock_exchange.entity.User;
import com.project.stock_exchange.service.Interfaces.UserService;
import com.project.stock_exchange.service.Interfaces.UserSignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
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

    @GetMapping("/")
    public String welcome(Model theModel)
    {
        return "index";
    }
    @GetMapping("/login_page")
    public String showLoginPage(Model theModel)
    {
        return "login/login-page";
    }
    @GetMapping("/signup_page")
    public String showSignupPage(Model theModel)
    {
        UserSignupDTO userSignupDTO = new UserSignupDTO();
        theModel.addAttribute("user_signup", userSignupDTO);
        return "login/signup-page";
    }

    @PostMapping("/signupSubmit")
    public String signUp(@ModelAttribute("user_signup") UserSignupDTO userSignupDTO,
                         Model theModel)
    {
        userSignupDTO.setEmail("N/A");
        userSignupDTO.setPassword("{noop}" + userSignupDTO.getPassword());
        userSignupDTO.setEnabled(true);
        userSignupDTO.setBalance(BigDecimal.valueOf(10000.000));
        userSignupDTO.setInvested(BigDecimal.valueOf(0));

        userSignupService.saveUser(userSignupDTO);
        return "login/login-page";
    }
    @PostMapping("/loginSubmit")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password)
    {
        password = "{noop}" + password;
        UserSignupDTO userSignupDTO = userSignupService.getUser(username, password);
        if(userSignupDTO == null)
        {
            return "login/login-page";
        }
        User user = userService.getAccountDetails(username);
        sessionID.setUser(user);
        // singleton class set karo and others
        return "redirect:/dashboard/list";

    }
}
