package ebong.badthinkingdiary.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
    public List<MemberRole> findByMemberId(Long userId);
}
