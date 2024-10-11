package com.hello.docs.controller;

import com.hello.docs.config.UserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

// @Tag 를 사용하여 API 그룹을 지정할 수 있습니다.
@Tag(
        name = "Swagger API Example",
        description = "Swagger API Example 입니다."
)
@RestController
public class RestApiController {

    // 메서드의 매개변수나 DTO 객체의 필드에 @Parameter 어노테이션을 사용하여 문서화할 수 있습니다.
    // 프로젝트를 실행시키고 http://localhost:8080/swagger-ui/index.html 에서 확인해보세요.

    // @Operation 을 사용하여 API 의 설명을 추가할 수 있습니다.
    @Operation(
            summary = "GET API",
            description = "GET API 입니다.",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "아이디",
                            required = true,
                            example = "1",
                            in = ParameterIn.PATH
                    ),
                    @Parameter(
                            name = "name",
                            description = "이름",
                            required = true,
                            example = "윤동주",
                            in = ParameterIn.QUERY
                    )
            },
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = HelloDTO.class))
            )
    )
    @GetMapping("/api/hello/{id}")
    public HelloDTO helloGet(
            @UserId Long userId,
            @PathVariable Long id,
            @RequestParam(defaultValue = "박성민") String name
    ) {
        return new HelloDTO(id, name, userId != null);
    }

    @Operation(
            summary = "POST API",
            description = "POST API 입니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody( // Spring 의 @RequestBody 와 이름이 겹칩니다.
                    description = "요청할 JSON",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공",
                            content = @Content(schema = @Schema(implementation = HelloDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "실패"
                    )
            }
    )
    @PostMapping("/api/hello")
    public HelloDTO helloPost(
            @RequestBody HelloDTO helloDTO
    ) {
        return helloDTO;
    }

    @Operation(
            summary = "POST API with File",
            description = "파일을 전송하는 POST API 입니다.",
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = HelloDTO.class))
            )
    )
    @PostMapping(
            path = "/api/hello/file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public HelloDTO helloFile(
            @Parameter(
                    name = "file",
                    description = "전송할 파일",
                    required = true
            )
            @RequestPart MultipartFile file
    ) {
        return new HelloDTO(1L, "name", true);
    }

    public record HelloDTO(
            @Schema(
                    name = "아이디",
                    description = "전송할 아이디",
                    example = "1"
            )
            Long id,
            @Schema(
                    name = "이름",
                    description = "전송할 이름",
                    example = "윤동주", // 문서에 보여주는 예시입니다.
                    defaultValue = "박성민" // 문서와 별개로 독자에게 기본값이 무엇인지를 알려줍니다.
            )
            String name,
            @Schema(
                    name = "성공 여부",
                    description = "성공 여부",
                    example = "true"
            )
            Boolean success
    ) {

    }
}
