package com.hello.docs.restdocs;

import com.hello.docs.controller.RestApiController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestApiDocsTest extends RestDocsEnvironment {

    // 테스트를 실행하면 build/generated-snippets 에 문서가 생성됩니다.
    // 이 문서들을 src/docs/asciidoc 에서 커스텀하여 문서를 만들고, 이 상태에서 build 를 하면 html 파일이 생성됩니다.
    // build 한 jar 파일을 실행하면, 현재 build.gradle 설정에 따라 {HOST}/rest-docs/index.html 에서 문서를 확인할 수 있습니다.

    // webAppContextSetup 환경에서는 필요에 따라 Mocking 하면 됩니다.
    // private final RestApiService restApiService = org.mockito.Mockito.mock(RestApiService.class);

    @DisplayName("GET Method 에 대한 문서를 작성할 수 있다.")
    @Test
    void helloGet() throws Exception {
        // given
        Long id = 1L;
        String requiredParam = "requiredParam";

        // when
        ResultActions resultActions = mockMvc.perform(
                get("/api/hello/{id}", id) // RestDocumentationRequestBuilders 를 사용함에 주의!
                        .param("requiredParam", requiredParam) // optionalParam 을 보내지 않았습니다.
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("hello-get-spec", // 원하는 문서 이름 지정
                        // path parameter 에 대한 설명
                        pathParameters(
                                parameterWithName("id").description("아이디")
                        ),
                        // query parameter 에 대한 설명
                        queryParameters(
                                parameterWithName("requiredParam").description("필수 파라미터"), // optional() 을 쓰지 않으면 기본적으로 필수임을 표시합니다.
                                parameterWithName("optionalParam").description("선택 파라미터").optional() // optional() 을 사용하면, 실제로 보내지 않은 파라미터도 문서화할 수 있습니다.
                        ),
                        // response body 인 json 에 대한 설명
                        responseFields(
                                fieldWithPath("id").type(NUMBER).description("아이디"), // type 과 description 으로 필드에 대한 설명을 작성합니다.
                                fieldWithPath("requiredParam").type(STRING).description("반드시 전달되는 응답값"),
                                fieldWithPath("optionalParam").type(STRING).description("NULL 이 가능한 응답값").optional(),
                                fieldWithPath("success").type(BOOLEAN).description("성공 여부")
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
                .andDo(document("hello-post-spec",
                        // request header 에 대한 설명
                        requestHeaders(
                                headerWithName("Content-Type").description("Http Header 중 Content-Type")
                        ),
                        // request body 인 json 에 대한 설명
                        requestFields(
                                fieldWithPath("id").type(NUMBER).description("아이디"),
                                fieldWithPath("name").type(STRING).description("이름"),
                                fieldWithPath("nullData").type(STRING).description("NULL 이 가능한 데이터").optional(),
                                fieldWithPath("success").type(BOOLEAN).description("성공 여부")
                        ),
                        responseFields(
                                fieldWithPath("id").type(NUMBER).description("아이디"),
                                fieldWithPath("requiredParam").type(STRING).description("반드시 전달되는 응답값"),
                                fieldWithPath("optionalParam").type(NULL).description("무조건 NULL 인 응답값"),
                                fieldWithPath("success").type(BOOLEAN).description("성공 여부")
                        )
                ));
    }

    @DisplayName("enctype 이 multipart/form-data 인 요청에 대해 문서를 작성할 수 있다.")
    @Test
    void helloFile() throws Exception {
        // given
        byte[] fileData = "file-content".getBytes();

        // when
        ResultActions resultActions = mockMvc.perform(
                multipart("/api/hello/file")
                        .file("file", fileData) // MockMultipartFile 을 사용할 수도 있습니다.
                        .contentType(MULTIPART_FORM_DATA_VALUE)
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(document("hello-file-spec",
                        requestHeaders(
                                headerWithName("Content-Type").description("Http Header 중 Content-Type")
                        ),
                        // request part 에 대한 설명
                        requestParts(
                                partWithName("file").description("파일")
                        ),
                        // 참고로 relaxedResponseFields 를 사용하면, 굳이 문서화하고 싶지 않은 필드들은 뺄 수 있습니다.
                        // 하지만, 그렇게 되면 정말로 응답값이 의도한 대로 나오는지 "테스트"할 수 없습니다. 적절히 트레이드 오프를 고려해주세요.
                        responseFields(
                                fieldWithPath("array").type(ARRAY).description("배열"),
                                fieldWithPath("array[].success").type(BOOLEAN).description("성공 여부"),
                                fieldWithPath("array[].message").type(STRING).description("메시지")
                        )
                ));
    }
}
