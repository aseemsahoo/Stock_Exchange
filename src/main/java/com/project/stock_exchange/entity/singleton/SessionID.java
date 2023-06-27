package com.project.stock_exchange.entity.singleton;

import com.project.stock_exchange.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@NoArgsConstructor
@Component
@SessionScope
public class SessionID
{
    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}

