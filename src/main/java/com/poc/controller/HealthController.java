package com.poc.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @Operation(summary = "health check")
    @GetMapping("/healthz")
    public Boolean healthz() {
        return true;
    }

}
