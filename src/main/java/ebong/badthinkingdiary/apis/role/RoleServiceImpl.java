package ebong.badthinkingdiary.apis.role;

import ebong.badthinkingdiary.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service("roleService")
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final MemberRoleRepository memberRoleRepository;


    @Override
    public Role findByName(RoleList name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void save(Role role){
        if(findByName(role.getName()) != null) {
            throw new DuplicateKeyException("already exist");
        }
        roleRepository.save(role);

    }

    @Override
    public void saveMemberRole(MemberRole memberRole) {

        if (memberRole.getRole() == null) {
            throw new IllegalArgumentException("not exist role");
        }

        List<MemberRole> membersRole = findMemberRoleById(memberRole.getMember().getId());
        if (membersRole != null) {
            for (MemberRole m : membersRole) {
                if (m.getRole().getName() == memberRole.getRole().getName()) {
                    throw new DuplicateKeyException("already exist");
                }
            }
        }

        memberRoleRepository.save(memberRole);
    }

    public List<MemberRole> findMemberRoleById(Long memberId){
        return memberRoleRepository.findByMemberId(memberId);
    }

    @Override
    public void deleteMemberRole(MemberRole memberRole) {
        memberRoleRepository.delete(memberRole);
    }

}
