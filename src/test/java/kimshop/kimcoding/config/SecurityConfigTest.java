package kimshop.kimcoding.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

//통합 테스트를 진행할 거고 MOCK(가짜)환경에서 진행.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@AutoConfigureMockMvc // Mock 환경에서 MockMvc가 등록됨
class SecurityConfigTest {

    // 가짜 환경에 등록된 MockMvc를 DI함
    @Autowired private MockMvc mockMvc;

    //서버는 일관성있게 에러가 리턴돼야 한다.
    // 내가 모르는 에러가 프론트로 가지 않게 직접 제어한다.
    @Test
    public void authentication_test() throws Exception{

        //MockMvcRequestBuilders를 임포트(static)해줘야 함.
        ResultActions resultActions = mockMvc.perform(get("/api/s/hello"));

        String responsBody = resultActions.andReturn().getResponse().getContentAsString();
        int httpCode = resultActions.andReturn().getResponse().getStatus();

        System.out.println("테스트 : "+responsBody);
        System.out.println("상태 코드 : "+httpCode);

        assertThat(httpCode).isEqualTo(401);
    }

    @Test
    public void authorization_test() throws Exception{

        //MockMvcRequestBuilders를 임포트(static)해줘야 함.
        ResultActions resultActions = mockMvc.perform(get("/api/admin/hello"));

        String responsBody = resultActions.andReturn().getResponse().getContentAsString();
        int httpCode = resultActions.andReturn().getResponse().getStatus();

        System.out.println("테스트 : "+responsBody);
        System.out.println("상태 코드 : "+httpCode);

        assertThat(httpCode).isEqualTo(401);
    }


}