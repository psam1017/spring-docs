package com.hello.docs.controller;

import jakarta.validation.constraints.NotNull;
import openapi.api.ApiApi;
import openapi.model.ApiHello;
import openapi.model.ApiHelloId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class RestApiController implements ApiApi {

    @Override
    public ResponseEntity<ApiHelloId> restApiDocsTestHelloGet(
            BigDecimal id,
            @NotNull String requiredParam,
            String optionalParam
    ) {
        return ResponseEntity.ok(new ApiHelloId().id(id));
    }

    @Override
    public ResponseEntity<ApiHelloId> restApiDocsTestHelloPost(ApiHello apiHello) {
        return ResponseEntity.ok(new ApiHelloId().id(apiHello.getId()));
    }
}
