package kimshop.kimcoding.domain.user;

import jakarta.persistence.*;
import kimshop.kimcoding.domain.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC) // 스프링 User 객체생성할 때 비어 있는 생성자로 new를 하기 때문!!
@Table(name = "user_tb")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 60) // 패스워드 인코딩(BCrypt)
    private String password;

    @Column(nullable = false, length = 20)
    private String email;

    @Column(nullable = false, length = 20)
    private String fullname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnum role; // ADMIN, CUSTOMER


    // ex). user.builder().username("user1").email("a@naver.com").build();
    @Builder
    public User(Long id, String username, String password, String email, String fullname, UserEnum role, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.role = role;
        this.setCreateAt(createdAt);
        this.setUpdateAt(updateAt);
    }
}
