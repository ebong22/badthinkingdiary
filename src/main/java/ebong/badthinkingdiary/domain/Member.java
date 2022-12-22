package ebong.badthinkingdiary.domain;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractEntity implements UserDetails {

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

    // 권한 관련 ====================================
    // @TODO 시큐리티 : role(권한) 맞게 만들어 줘야함 일단 임시로 따라서만 해보는중
//    private List<String> roles; // 나중에 String 말고 Role 만들어주기
    // 여기까지가 권한 관련 ====================================


    public Member(String userId, String userPw, String nickName) {
        this.userId     = userId;
        this.userPw     = userPw;
        this.nickName   = nickName;
    }

    @Builder
    public Member(String userId, String userPw, String nickName, String phoneNumber, LocalDate birthDay) {
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


    /**
     *  UserDetails 관련
     *  @TODO 시큐리티 : getPassword / getUsername 맞추기 위해 field명 password / username으로 바꾸는것 고려해보기
     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    /**
     * @TODO 시큐리티 : 아래 인증부분 현재 status 밖에 없어서 동일하게 처리했는데 좀더 상세화 해야할지 생각해보기
     */
    // status : 0 잠김(로그인횟수 초과) / 1 정상 / 2 휴면
    @Override
    public boolean isAccountNonExpired() { // 사용자 계정이 만료되었는지 여부
        if(this.status == '1'){
            return true;
        }
        return false;
    }

    @Override
    public boolean isAccountNonLocked() { // 사용자가 잠겼는지 잠금 해제되었는지
        if(this.status == '1'){
            return true;
        }
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 사용자의 자격 증명(암호)이 만료되었는지 여부
        if(this.status == '1'){
            return true;
        }
        return false;
    }

    @Override
    public boolean isEnabled() { // 사용자의 활성화 또는 비활성화 여부
        if(this.status == '1'){
            return true;
        }
        return false;
    }

    //@TODO 시큐리티 : 권한 가져오는 메소드 나중에 DB에서 권한가져오든 뭐든 수정해야함
    private Collection<SimpleGrantedAuthority> getRoles(List<String> roles) {
        roles.add("USER"); // 임시 : USER 권한으로 테스트
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // @TODO 임시 (추후 삭제)
    private Collection<SimpleGrantedAuthority> getRoles() {
        List<String> roles = new ArrayList<>();
        roles.add("USER"); // 임시 : USER 권한으로 테스트
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
