package ebong.badthinkingdiary.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String refreshToken;

    //@TODONOW 이거 뭔가 매핑 잘 안되는중 계속 null 로 들어가는 중 ....*************
//    @OneToOne(mappedBy = "refreshToken")
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private Instant expireDate;

    @Builder
    public RefreshToken(String refreshToken, Member member, Instant expireDate) {
        this.refreshToken = refreshToken;
        this.member = member;
        this.expireDate = expireDate;
    }

    public void updateToken(String refreshToken, Instant expireDate){
        this.refreshToken = refreshToken;
        this.expireDate = expireDate;
    }
}
