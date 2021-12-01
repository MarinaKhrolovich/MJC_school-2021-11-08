package com.epam.esm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloSpring {

    @GetMapping("/hello-world")
    public String SayHello(){
        return "hello_world";
    }
}
