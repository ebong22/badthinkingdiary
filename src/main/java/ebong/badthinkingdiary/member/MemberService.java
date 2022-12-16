package ebong.badthinkingdiary.member;

import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.dto.MemberUpdateDTO;

import java.util.List;

public interface MemberService {
    Long save(Member member);

    Member findById(Long id);

    List<Member> findAll();

    Member memberUpdate(MemberUpdateDTO updateDTO);
}
