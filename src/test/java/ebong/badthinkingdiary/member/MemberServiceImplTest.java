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
        Member member = new Member("ServiceTest", "ServiceTest123","ServiceTestNickName");

        Long saveId = memberService.save(member);

        Member findMember = memberService.findById(saveId);

        assertThat(saveId).isEqualTo(findMember.getId());
        assertThat(findMember.getUserId()).isEqualTo(member.getUserId());
        assertThat(findMember.getUserPw()).isEqualTo(member.getUserPw());

    }

    @Test
    public void canNotFindMemberTest() {
        List<Member> members = memberService.findAll();

        assertThatThrownBy(() -> memberService.findById(members.size() + 1L))
                .isInstanceOf(NoSuchElementException.class);

    }

}