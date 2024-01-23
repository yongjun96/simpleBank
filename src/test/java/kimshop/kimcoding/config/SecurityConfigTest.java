package kimshop.kimcoding.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.WatchEvent;

import static org.junit.jupiter.api.Assertions.*;

//통합 테스트를 진행할 거고 MOCK(가짜)환경에서 진행.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
class SecurityConfigTest {

    @Test
    public void _test() throws Exception{

    }


}