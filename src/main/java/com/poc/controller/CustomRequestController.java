package com.poc.controller;

import com.poc.repository.CustomMongoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "request")
public class CustomRequestController {

    private final CustomMongoRepository customMongoRepository;

    @Operation(summary = "WS used to execute request")
    @PostMapping
    public Object executeRequest(
            @Parameter(in = ParameterIn.QUERY, name = "request", schema = @Schema(example = "{\n" +
                    "    \"find\": \"notes\",\n" +
                    "    \"batchSize\": 10,\n" +
                    "    \"filter\": {\"title\": \"Spring Data\"}\n" +
                    "}"))
            @RequestBody String request) {
        return customMongoRepository.executMongoRequest(request);
    }

}
