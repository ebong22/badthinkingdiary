package ebong.badthinkingdiary.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private String userPw;

    @NotNull
    private String nickName;

    private String phoneNumber;

    private LocalDate birthDay;

    @Column(columnDefinition = "integer default 0")
    private int loginCount; // 로그인 시도 횟수 ( 5번? / 로그인 성공 하면 0으로 초기화 )

    private char status; // 0 잠김(로그인횟수 초과) / 1 정상 / 2 휴면

    private LocalDateTime createDate;

    private LocalDateTime lastLoginDate;

    public Member(String userId, String userPw, String nickNAme) {
        this.userId = userId;
        this.userPw = userPw;
        this.nickName = nickName;
    }

    @Builder
    public Member(String userId, String userPw, String nickName, String phoneNumber, LocalDate birthDay, char status) {
        this.userId = userId;
        this.userPw = userPw;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
    }

    /**
     * 회원가입 시 계정 상태, 가입일 세팅
     */
    public void setSignUpData(){
        this.status = '1';
        this.createDate = LocalDateTime.now();
    }
}
