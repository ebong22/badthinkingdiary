package ebong.badthinkingdiary.member;


import ebong.badthinkingdiary.domain.Member;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


//@DataJpaTest
@Slf4j
@SpringBootTest
@Transactional
class MemberServiceImplTest {

@Autowired
    private MemberService memberService;

    @Test
    public void saveTest(){
        Member member = mockMember();
        Member findMember =  memberService.save(mockMember());

        assertThat(findMember.getUserId()).isEqualTo(member.getUserId());
        assertThat(findMember.getUserPw()).isEqualTo(member.getUserPw());

    }

    @Test
    public void canNotFindMemberTest() {
        List<Member> members = memberService.findAll();

        assertThatThrownBy(() -> memberService.findById(members.size() + 1L))
                .isInstanceOf(NoSuchElementException.class);

    }

    @Test
    public void updateTest(){
        Member member = memberService.save(mockMember());

        member.memberUpdate("password", "nickname");

        assertThat(member.getUserPw()).isEqualTo("password");
        assertThat(member.getNickName()).isEqualTo("nickname");
    }

    @Test
    public void deleteTest(){
        Member member = memberService.save(mockMember());
        memberService.delete(member.getId());

        assertThatThrownBy(() -> memberService.findById(member.getId()))
                .isInstanceOf(NoSuchElementException.class);
    }

    private Member mockMember(){
        return new Member("ServiceTest", "ServiceTest123","ServiceTestNickName");
    }



}