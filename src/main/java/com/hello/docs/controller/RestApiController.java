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
            @RequestParam String name
    ) {
        return Map.of(
                "id", id,
                "name", name,
                "success", true
        );
    }

    @PostMapping("/api/hello")
    public Object helloPost(
            @RequestBody HelloDTO helloDTO
    ) {
        return helloDTO;
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

    private static class HelloDTO {

        private Long id;
        private String name;
        private Boolean success;

        public HelloDTO() {
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Boolean getSuccess() {
            return success;
        }
    }
}
