package com.hello.docs.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class RestApiController {

    @GetMapping("/api/hello/{id}")
    public Object helloGet(
            @PathVariable Long id,
            @RequestParam String requiredParam,
            @RequestParam(required = false) String optionalParam
    ) {
        return new HelloResponse(id, requiredParam, optionalParam, true);
    }

    @PostMapping("/api/hello")
    public Object helloPost(
            @RequestBody HelloRequest helloRequest
    ) {
        return new HelloResponse(helloRequest.id(), "requiredParam", null, false);
    }

    @PostMapping(
            path = "/api/hello/file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Object helloFile(
            @RequestPart MultipartFile file
    ) {
        return Map.of(
                "array", List.of(
                        Map.of(
                                "success", true,
                                "message", "Hello, Docs!"
                        )
                )
        );
    }

    public record HelloRequest(
            Long id,
            String name,
            String nullData,
            Boolean success
    ) {

    }

    public record HelloResponse(
            Long id,
            String requiredParam,
            String optionalParam,
            Boolean success
    ) {

    }
}
