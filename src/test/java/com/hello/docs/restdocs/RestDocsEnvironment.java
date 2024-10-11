package com.hello.docs.restdocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;

@Import(RestDocsConfig.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
public abstract class RestDocsEnvironment {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    private final ObjectMapper objectMapper = new ObjectMapper();

    protected String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
