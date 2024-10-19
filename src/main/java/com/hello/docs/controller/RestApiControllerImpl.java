package com.hello.docs.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RestApiControllerImpl implements RestApiController {

    @Override
    public HelloDTO helloGet(Long userId, Long id, String name) {
        return new HelloDTO(id, name, userId != null);
    }

    @Override
    public HelloDTO helloPost(HelloDTO helloDTO) {
        return helloDTO;
    }

    @Override
    public HelloDTO helloFile(MultipartFile file) {
        return new HelloDTO(1L, "name", true);
    }
}
