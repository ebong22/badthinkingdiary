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
