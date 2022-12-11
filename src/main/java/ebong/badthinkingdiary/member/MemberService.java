package ebong.badthinkingdiary.member;

import ebong.badthinkingdiary.domain.Member;

import java.util.List;

public interface MemberService {
    Long save(Member member);

    Member findById(Long id);

    List<Member> findAll();
}
