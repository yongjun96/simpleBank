package kimshop.kimcoding.Dto.user;

import kimshop.kimcoding.domain.user.User;
import kimshop.kimcoding.util.CustomDateUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class UserRespDto {

    @Data
    public static class LoginRespDto{
        private Long id;
        private String username;
        private String createAt;

        public LoginRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.createAt = CustomDateUtil.toStringFormat(user.getCreateAt());
        }
    }

    @ToString
    @Getter
    @Setter
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
}
