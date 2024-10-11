package com.hello.docs.restdocs;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.hello.docs.controller.RestApiController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestApiDocsTest extends RestDocsEnvironment {

    // https://github.com/ePages-de/restdocs-api-spec 에서 문서를 확인하실 수 있습니다.

    // gradlew openapi3 를 실행하면 build/api-spec 에 yaml 파일이 생성됩니다.
    // 생성된 yaml 은 editor.swagger.io 에서 테스트하거나, docker 로 swagger-ui 를 실행하여 확인할 수 있습니다.

    @DisplayName("GET Method 에 대한 문서를 작성할 수 있다.")
    @Test
    void helloGet() throws Exception {
        // given
        Long id = 1L;
        String requiredParam = "requiredParam";

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/hello/{id}", id)
                        .param("requiredParam", requiredParam)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        resource(
                                ResourceSnippetParameters.builder()
                                        .description("GET Method API")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.NUMBER).description("아이디")
                                        )
                                        .queryParameters(
                                                parameterWithName("requiredParam").type(SimpleType.STRING).description("반드시 전달되어야 하는 파라미터"),
                                                parameterWithName("optionalParam").type(SimpleType.STRING).description("선택적으로 전달되는 파라미터").optional()
                                        )
                                        .responseFields(
                                                fieldWithPath("id").type(SimpleType.NUMBER).description("아이디"),
                                                fieldWithPath("requiredParam").type(SimpleType.STRING).description("반드시 전달되는 응답값"),
                                                fieldWithPath("optionalParam").type(SimpleType.STRING).description("선택적으로 전달되는 응답값"),
                                                fieldWithPath("success").type(SimpleType.BOOLEAN).description("성공 여부")
                                        )
                                        .build()
                        )
                ));
    }

    @DisplayName("GET Method 에 대한 문서를 작성할 수 있다.")
    @Test
    void helloPost() throws Exception {
        // given
        RestApiController.HelloRequest helloRequest = new RestApiController.HelloRequest(
                1L,
                "name",
                null,
                true
        );

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/hello")
                        .contentType(APPLICATION_JSON)
                        .content(toJson(helloRequest))
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        resource(
                                ResourceSnippetParameters.builder()
                                        .description("POST Method API")
                                        .requestFields(
                                                fieldWithPath("id").type(SimpleType.NUMBER).description("아이디"),
                                                fieldWithPath("name").type(SimpleType.STRING).description("이름"),
                                                fieldWithPath("nullData").type(SimpleType.STRING).description("NULL 이 가능한 데이터").optional(),
                                                fieldWithPath("success").type(SimpleType.BOOLEAN).description("성공 여부")
                                        )
                                        .responseFields(
                                                fieldWithPath("id").type(SimpleType.NUMBER).description("아이디"),
                                                fieldWithPath("requiredParam").type(SimpleType.STRING).description("반드시 전달되는 응답값"),
                                                fieldWithPath("optionalParam").type(SimpleType.STRING).description("선택적으로 전달되는 응답값").optional(), // SimpleType 에는 NULL 이 없습니다.
                                                fieldWithPath("success").type(SimpleType.BOOLEAN).description("성공 여부")
                                        )
                                        .build()
                        )
                ));
    }

    // Multipart 는 현재 지원되지 않는 것처럼 보입니다.
    // https://github.com/ePages-de/restdocs-api-spec/issues/105
}
