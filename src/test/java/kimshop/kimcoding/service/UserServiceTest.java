package kimshop.kimcoding.service;

import kimshop.kimcoding.Dto.user.UserReqDto;
import kimshop.kimcoding.Dto.user.UserRespDto;
import kimshop.kimcoding.config.dummy.DummyObject;
import kimshop.kimcoding.domain.user.User;
import kimshop.kimcoding.domain.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
// spring 관련 bean들이 하나도 없는 환경!!
class UserServiceTest extends DummyObject {

    //가짜 환경으로 세팅해 줌
    // @Autowired 아님!!
    // @InjectMocks에 @Mock를 주입해 줌
    // 진짜를 주입 @Spy / 가짜를 주입 @Mock
    @InjectMocks
    private UserService userService;

    @Mock // 가짜로 띄워 줌
    private UserRepository userRepository;

    @Spy // 스프링 ioc에 있는 진짜를 꺼내서 @InjectMocks에 주입
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void joinTest(){

        //given
        UserReqDto.JoinReqDto userReqDto = new UserReqDto.JoinReqDto();
        userReqDto.setUsername("ssar");
        userReqDto.setPassword("1234");
        userReqDto.setEmail("ssar@nate.com");
        userReqDto.setFullname("쌀");

        //stub : 가정법
        //any() : 뭐든지 간에 들어 갔다면 이라고 가정
        //Optional.empty() : 비어 있는 Optional를 반환 한다고 가정
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        //when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User())); // 실패 테스트

        //stub 2
        User ssar = newMockUser(100L, "ssar", "쌀");
        when(userRepository.save(any())).thenReturn(ssar);

        //when
        UserRespDto.JoinRespDto userRespDto = userService.join(userReqDto);

        //then
        System.out.println("테스트 : "+userRespDto);
        Assertions.assertThat(userRespDto.getId()).isEqualTo(100L);
        Assertions.assertThat(userRespDto.getUsername()).isEqualTo("ssar");
    }
}