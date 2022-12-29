package ebong.badthinkingdiary.apis.role;

import ebong.badthinkingdiary.domain.MemberRole;
import ebong.badthinkingdiary.domain.Role;
import ebong.badthinkingdiary.domain.RoleList;

public interface RoleService {

    Role findByName(RoleList name);

    void save(Role role);

    void saveMemberRole(MemberRole memberRole);

    void deleteMemberRole(MemberRole memberRole);
}
