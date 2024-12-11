package com.example.demo;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.example.demo.controller.UserApiController;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;  // 응답 검증


@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class UserApiDocumentation {
    @Autowired
    private MockMvc mockMvc;

    @Mock // UserService를 모킹
    private  UserService userService;

    @InjectMocks // 모의 객체를 UserApiController에 주입
    private UserApiController userApiController;


    @Test
    public void testRead() throws Exception {
        // Mocking UserService behavior
        User mockUser = new User(1L, "user1", "user1@example.com", "010-1234-5678",
                LocalDateTime.now(), LocalDateTime.now());

        Mockito.when((userService.read(1L))).thenReturn(mockUser);


        mockMvc.perform(get("/api/user/{id}", 1)) // GET 요청
                .andExpect(status().isOk()) // 200 상태 확인
                .andDo(document("user", // REST Docs 스니펫 이름
                        pathParameters(
                                parameterWithName("id").description("사용자 ID")
                        ),
                        responseFields(
                                fieldWithPath("resultCode").description("응답 코드"),
                                fieldWithPath("data.id").description("사용자 ID"),
                                fieldWithPath("data.account").description("사용자 계정"),
                                fieldWithPath("data.email").description("사용자 이메일"),
                                fieldWithPath("data.phoneNumber").description("사용자 전화번호"),
                                fieldWithPath("data.createAt").description("계정 생성 시간"),
                                fieldWithPath("data.updateAt").description("계정 수정 시간")
                        )
                ));

        // 스웨거
        mockMvc.perform(get("/api/user/{id}",1))
                .andDo(document("user",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(
                                ResourceSnippetParameters.builder()
                                        .description("사용자의 정보를 생성/조회/수/삭제 합니다.")
                                        .summary("사용자 정보")
                                        .pathParameters(
                                        parameterWithName("id").description("사용자 Id")
                                ).
                                responseFields(
                                        fieldWithPath("resultCode").description("응답 코드"),
                                        fieldWithPath("data.id").description("사용자 ID"),
                                        fieldWithPath("data.account").description("사용자 계정"),
                                        fieldWithPath("data.email").description("사용자 이메일"),
                                        fieldWithPath("data.phoneNumber").description("사용자 전화번호"),
                                        fieldWithPath("data.createAt").description("계정 생성 시간"),
                                        fieldWithPath("data.updateAt").description("계정 수정 시간")
                                ).build()
                        )
                ));
    }


}
