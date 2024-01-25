package kimshop.kimcoding.config.dummy;

import kimshop.kimcoding.domain.user.User;
import kimshop.kimcoding.domain.user.UserEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class DummyObject {

    //실제로 Entity에 save할 때 사용
    protected User newUser(String username, String fullname){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");

        return User.builder()
                .username(username)
                .password(encPassword)
                .email(username+"@nate.com")
                .fullname(fullname)
                .role(UserEnum.CUSTOMER)
                .build();

    }

    //테스트 할 때 사용 (가짜로 생성할 때 사용)
    protected User newMockUser(Long id, String username, String fullname){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");

        return User.builder()
                .id(id)
                .username(username)
                .password(encPassword)
                .email(username+"@nate.com")
                .fullname(fullname)
                .role(UserEnum.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }
}
