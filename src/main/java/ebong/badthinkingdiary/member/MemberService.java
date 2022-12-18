package ebong.badthinkingdiary.member;

import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.dto.MemberUpdateDTO;

import java.util.List;

public interface MemberService {
    Member save(Member member);

    Member findById(Long id);

    List<Member> findAll();

    Member update(MemberUpdateDTO updateDTO);

    void delete(Long id);
}
