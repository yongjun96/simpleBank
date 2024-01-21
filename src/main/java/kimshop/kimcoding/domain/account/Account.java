package kimshop.kimcoding.domain.account;

import jakarta.persistence.*;
import kimshop.kimcoding.domain.base.BaseTimeEntity;
import kimshop.kimcoding.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "account_tb")
@Getter
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private Long number; // 계좌 번호

    @Column(nullable = false, length = 4)
    private Long password; //계좌 비번

    @Column(nullable = false)
    private Long balance; // 잔액 (기본값 : 1000)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Account(Long id, Long number, Long password, Long balance, User user) {
        this.id = id;
        this.number = number;
        this.password = password;
        this.balance = balance;
        this.user = user;
    }
}
