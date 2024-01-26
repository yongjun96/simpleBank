package kimshop.kimcoding.Dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.websocket.OnMessage;
import kimshop.kimcoding.domain.user.User;
import kimshop.kimcoding.domain.user.UserEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserReqDto{

    //컨트롤러를 가기 전에 쓰이기 때문에 밸리데이션 체크 못함.
    @Data
    public static class LoginReqDto{
        private String username;
        private String password;
    }


    @Data
    public static class JoinReqDto {

        @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "영문/숫자 2~20자 이내로 작성해 주세요.")
        @NotEmpty
        private String username;

        @Size(min = 4, max = 20, message = "4~20자 사이로 작성해 주세요.")
        @NotEmpty
        private String password;

        @Pattern(regexp = "^[a-zA-Z0-9]{1,20}@[a-zA-Z0-9]{1,20}\\.[a-zA-Z]{1,10}$", message = "이메일 형식으로 작성해 주세요.")
        @NotEmpty
        private String email;

        @Pattern(regexp = "^[a-zA-Z가-힣]{2,20}$", message = "한글/영문 2~20자 이내로 작성해 주세요.")
        @NotEmpty
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
