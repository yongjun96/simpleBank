package kimshop.kimcoding.Dto.user;

import jakarta.validation.constraints.NotEmpty;
import kimshop.kimcoding.domain.user.User;
import kimshop.kimcoding.domain.user.UserEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserReqDto{

    @Getter
    @Setter
    public static class JoinReqDto {

        @NotEmpty(message = "username은 필수 값 입니다.") // null이거나 공백일 수 없다.
        private String username;
        @NotEmpty(message = "password는 필수 값 입니다.")
        private String password;
        @NotEmpty(message = "email는 필수 값 입니다.")
        private String email;
        @NotEmpty(message = "fullname는 필수 값 입니다.")
        private String fullname;

        public User toEntity(BCryptPasswordEncoder passwordEncoder) {
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
