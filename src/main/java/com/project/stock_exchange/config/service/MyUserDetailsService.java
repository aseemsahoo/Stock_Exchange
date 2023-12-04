package com.project.stock_exchange.config.service;

import com.project.stock_exchange.entity.User;
//import com.project.stock_exchange.entity.dto.UserSignupDTO;
import com.project.stock_exchange.service.interfaces.UserSignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

//@Service
//public class MyUserDetailsService implements UserDetailsService {
//    @Autowired
//    private UserSignupService userSignupService;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        try{
//            UserSignupDTO user = userSignupService.findByUsername(username);
//            return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
//        }
//        catch(Exception ex)
//        {
//            return (UserDetails) ex;
//        }
//    }
//}

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserSignupService userSignupService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userRes = Optional.ofNullable(userSignupService.findByUsername(username));
        if(userRes.isEmpty())
            throw new UsernameNotFoundException("Could not find user with username: " + username);
        User user = userRes.get();
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+ user.getRole())));
    }
}