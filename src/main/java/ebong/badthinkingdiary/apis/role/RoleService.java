package ebong.badthinkingdiary.apis.role;

import ebong.badthinkingdiary.domain.MemberRole;
import ebong.badthinkingdiary.domain.Role;
import ebong.badthinkingdiary.domain.RoleList;

import java.util.List;

public interface RoleService {

    /**
     * Role 단건조회 - by RoleName
     * @param name
     * @return
     */
    Role find(RoleList name);

    /**
     * Role 저장
     * @param role
     */
    void save(Role role);

    /**
     * MembeRole 저장
     *
     * @param memberRole
     * @return
     */
    MemberRole saveMemberRole(MemberRole memberRole);

    /**
     * MemberRole 조회
     * @param memberId
     * @return
     */
    List<MemberRole> findMemberRole(Long memberId);

    /**
     * MemberRole 삭제
     * @param memberRole
     */
    void deleteMemberRole(MemberRole memberRole);
}
