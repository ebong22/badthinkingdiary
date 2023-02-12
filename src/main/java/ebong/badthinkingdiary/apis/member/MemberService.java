package ebong.badthinkingdiary.apis.member;

import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.domain.MemberRole;
import ebong.badthinkingdiary.dto.MemberUpdateDTO;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

public interface MemberService {

    /**
     * Member 저장
     * @param member
     * @return
     * @throws DuplicateKeyException
     */
    Member save(Member member);

    /**
     * Memeber 단건조회 - by id
     * @param id
     * @return Member
     */
    Member find(Long id);

    /**
     * Memeber 단건조회 - by userId
     * @param userId
     * @return
     */
    Member find(String userId);

    /**
     * Member 전체조회
     * @return Member List
     */
    List<Member> findAll();

    /**
     * Member 수정
     * @param updateDTO
     * @return
     */
    Member update(MemberUpdateDTO updateDTO);

    /**
     * Member 삭제
     * @param id
     */
    void delete(Long id);

    /**
     * MemeberRole 조회 -by MemberId
     * @param id
     * @return
     */
    List<MemberRole> getMemberRole(Long memberId);

    /**
     * MemberRoles to String List
     * @param memberRoles
     * @return String List
     */
    List<String> memberRolesToList(List<MemberRole> memberRoles);
}
