package ebong.badthinkingdiary.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Member extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private String userPw;

    @NotNull
    private String nickNAme;

    private String phoneNumber;

    private LocalDate birthDay;

    private int loginCount; // 로그인 시도 횟수 ( 5번? / 로그인 성공 하면 0으로 초기화 )

    private char status; // 0 잠김(로그인횟수 초과) / 1 정상 / 2 휴면

    private LocalDateTime createDate;

    private LocalDateTime lastLoginDate;

    public Member(String userId, String userPw, String nickNAme) {
        this.userId = userId;
        this.userPw = userPw;
        this.nickNAme = nickNAme;
    }
}
