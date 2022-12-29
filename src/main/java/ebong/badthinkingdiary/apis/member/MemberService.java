package ebong.badthinkingdiary.apis.member;

import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.domain.MemberRole;
import ebong.badthinkingdiary.domain.RoleList;
import ebong.badthinkingdiary.dto.MemberUpdateDTO;

import java.util.List;

public interface MemberService {
    Member save(Member member);

    Member findById(Long id);

    List<Member> findAll();

    Member findByUserId(String userId);

    Member update(MemberUpdateDTO updateDTO);

    void delete(Long id);

    List<MemberRole> getMemberRole(Long id);
}
