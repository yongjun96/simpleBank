package kimshop.kimcoding.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import kimshop.kimcoding.Dto.user.UserReqDto;
import kimshop.kimcoding.config.dummy.DummyObject;
import kimshop.kimcoding.domain.user.User;
import kimshop.kimcoding.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static kimshop.kimcoding.Dto.user.UserReqDto.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Rollback(value = false)
// 통합테스트
class UserControllerTest extends DummyObject {

    @Autowired private MockMvc mvc;

    @Autowired private ObjectMapper om;

    @Autowired private UserRepository userRepository;


    @Test
    public void joinSuccessTest() throws Exception {

        //given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("yongjun");
        joinReqDto.setFullname("kimyongjun");
        joinReqDto.setEmail("yongjun96@gmail.com");
        joinReqDto.setPassword("1234");

        String requestBody = om.writeValueAsString(joinReqDto);
        System.out.println("테스트 : "+requestBody);

        //when
        ResultActions resultActions = mvc.perform(post("/api/join").content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        resultActions.andExpect(status().isCreated());

    }

    @Test
    public void joinFailTest() throws Exception {

        //given
        userRepository.save(newUser("amugea", "아무개"));

        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("amugea");
        joinReqDto.setFullname("아무개");
        joinReqDto.setEmail("amugea96@gmail.com");
        joinReqDto.setPassword("1234");

        String requestBody = om.writeValueAsString(joinReqDto);
        System.out.println("테스트 : "+requestBody);

        //when
        ResultActions resultActions = mvc.perform(post("/api/join").content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+responseBody);

        //then
        resultActions.andExpect(status().isBadRequest());

    }
}