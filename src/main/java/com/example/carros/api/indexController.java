package com.example.carros.api;

import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/")
public class indexController {

    @GetMapping()
    public String get () {
        return "API dos Carros";
    }
}
