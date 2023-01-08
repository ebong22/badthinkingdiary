package ebong.badthinkingdiary.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String token);
    Optional<RefreshToken> findByMemberId(Long memberId);
    Optional<RefreshToken> findByMember_userId(String memberId);

    void deleteAllByExpireDateLessThan(Instant now);

    void deleteByMemberId(Long memberId);

}
