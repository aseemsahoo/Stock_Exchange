package com.project.stock_exchange.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/dashboard")
public class RestController
{
    // injecting values from application.properties
    @Value("${owner.name}")
    private String name;
    @Value("${company.name}")
    private String company;

    @GetMapping("/")
    public String welcome_display()
    {
        return "Hello ! This is a project made by " + name + ", who studies in " + company;
    }
}