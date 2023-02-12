package ebong.badthinkingdiary.apis.member;


import ebong.badthinkingdiary.apis.role.RoleService;
import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.domain.MemberRole;
import ebong.badthinkingdiary.domain.Role;
import ebong.badthinkingdiary.domain.RoleList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


//@DataJpaTest
@SpringBootTest
@Transactional
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private RoleService roleService;

    @Test
    public void save(){
        Member member = mockMember();
        Member findMember =  memberService.save(mockMember());

        assertThat(findMember.getUserId()).isEqualTo(member.getUserId());
        assertThat(findMember.getUserPw()).isEqualTo(member.getUserPw());

    }

    @Test
    void findById() {
        Member member = mockMember();
        Member saveMember = memberService.save(member);

        assertThat(memberService.find(member.getId())).isEqualTo(saveMember);
    }

    @Test
    void findByUserId() {
        Member member = mockMember();
        Member saveMember = memberService.save(member);

        assertThat(memberService.find(member.getUserId())).isEqualTo(saveMember);
    }

    @Test
    void findAll() {
        Member member1 = mockMember();
        Member saveMember1 = memberService.save(member1);

        Member member2 = mockMember("testUser2", "testUser2");
        Member saveMember2 = memberService.save(member2);

        assertThat(memberService.findAll()).contains(member1, member2);
    }

    @Test
    public void canNotFindMemberTest() {
        List<Member> members = memberService.findAll();

        assertThatThrownBy(() -> memberService.find(members.size() + 1L))
                .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    public void update(){
        Member member = memberService.save(mockMember());

        member.memberUpdate("password", "nickname");

        assertThat(member.getUserPw()).isEqualTo("password");
        assertThat(member.getNickName()).isEqualTo("nickname");
    }

    @Test
    public void delete(){
        Member member = memberService.save(mockMember());
        memberService.delete(member.getId());

        assertThatThrownBy(() -> memberService.find(member.getId()))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void getMemberRole() {
        Member member = mockMember();
        memberService.save(member);
        Role role = roleService.find(RoleList.USER);

        MemberRole memberRole = mockMemberRole(member, role);
        MemberRole saveMemberRole = roleService.saveMemberRole(memberRole);
        assertThat(memberService.getMemberRole(member.getId())).contains(saveMemberRole);
    }

    @Test
    void memberRolesToList() {

        Member member = mockMember();
        memberService.save(member);

        Role roleUser = roleService.find(RoleList.USER);
        MemberRole memberRoleUser = mockMemberRole(member, roleUser);
        MemberRole saveMemberRoleUser = roleService.saveMemberRole(memberRoleUser);

        Role roleAdmin = roleService.find(RoleList.ADMIN);
        MemberRole memberRoleAdmin = mockMemberRole(member, roleAdmin);
        MemberRole saveMemberRoleAdmin = roleService.saveMemberRole(memberRoleAdmin);

        List<MemberRole> memberRoles = memberService.getMemberRole(member.getId());
        List<String> roleStringArr = memberService.memberRolesToList(memberRoles);

        List<String> refArr = new ArrayList<>();
        refArr.add(RoleList.USER.name());
        refArr.add(RoleList.ADMIN.name());

        assertThat(roleStringArr).isEqualTo(refArr);
    }

    private Member mockMember(){
        return new Member("ServiceTest", "ServiceTest123","ServiceTestNickName");
    }

    private Member mockMember(String userId, String nickName){
        return new Member(userId, "ServiceTest123",nickName);
    }

    private MemberRole mockMemberRole(Member member, Role role){
        return MemberRole.builder()
                .member(member)
                .role(role)
                .build();
    }

}