package ebong.badthinkingdiary.domain;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
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

    public Member(String userId, String userPw, String nickName) {
        this.userId     = userId;
        this.userPw     = userPw;
        this.nickName   = nickName;
    }

    @Builder
    public Member(String userId, String userPw, String nickName, String phoneNumber, LocalDate birthDay, char status) {
        this.userId     = userId;
        this.userPw     = userPw;
        this.nickName   = nickName;
        this.phoneNumber = phoneNumber;
        this.birthDay   = birthDay;
    }

    /**
     * 회원가입 시 계정 상태, 가입일 세팅
     */
    public void setSignUpData(){
        this.status     = '1';
        this.createDate = LocalDateTime.now();
    }

    /**
     * 회원정보 수정
     * @param userPw
     * @param nickName
     */
    public void memberUpdate(String userPw, String nickName){
        if( userPw != null &&  !userPw.isEmpty() ){
            this.userPw = userPw;
        }

        if( nickName != null &&  !nickName.isEmpty() ){
            this.nickName = nickName;
        }
    }


    /**
     * 로그인 횟수 카운팅
     */
    public void loginCounting(){
        loginCount++;
        if (loginCount == 5) { // @TODO now : 여기서 status 바로 0으로 업데이트 되면 그 이후 로그인이 막히게 처리 ( !1 인 계정은 로그인 불가처리)
            status = 0;
        }
    }

    /**
     * 계정 상태 변경
     * @param status
     */
    public void setStatus(char status){
        this.status = status;
    }
}
