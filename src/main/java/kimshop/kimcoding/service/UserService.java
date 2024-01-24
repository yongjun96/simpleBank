package kimshop.kimcoding.service;

import kimshop.kimcoding.domain.user.User;
import kimshop.kimcoding.domain.user.UserEnum;
import kimshop.kimcoding.domain.user.UserRepository;
import kimshop.kimcoding.ex.CustomApiException;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * Dto를 요청받고, Dto로 응답한다.
     */
    @Transactional
     public JoinRespDto join(JoinReqDto joinReqDto){

        // 1. 동일 유저네임 존재 검사
        // 2. 패스워드 인코딩
        // 3. dto 응답

        Optional<User> findUser = userRepository.findByUsername(joinReqDto.username);

        //findUser가 존재 한다면.
        if(findUser.isPresent()){
            // 존재한다면 중복되었다는 뜻.
            throw new CustomApiException("동일한 유저이름이 존재합니다.");
        }

        User userPs = userRepository.save(joinReqDto.toEntity(passwordEncoder));

        return new JoinRespDto(userPs);

     }

     @Data
     public static class JoinRespDto{
        private Long id;
        private String username;
        private String fullname;


         public JoinRespDto(User user) {
             this.id = user.getId();
             this.username = user.getUsername();
             this.fullname = user.getFullname();
         }
     }

     @Data
     public static class JoinReqDto{
        //유효성 검사
        private String username;
        private String password;
        private String email;
        private String fullname;

        public User toEntity(BCryptPasswordEncoder passwordEncoder){
            return User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .fullname(fullname)
                    .role(UserEnum.CUSTOMER)
                    .build();
        }
     }

}
