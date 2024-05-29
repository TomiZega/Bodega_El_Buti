package com.diseno.ppai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class Gestor {
    @GetMapping("/vinos")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
}
