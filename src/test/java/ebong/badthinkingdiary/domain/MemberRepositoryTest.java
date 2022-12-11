package ebong.badthinkingdiary.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

// DataJpaTest: 쿼리 안날라가고 jpa 영속성 컨텍스트에 대해서만 test | SpringBootTest는 쿼리 날아감
// DataJpaTest: @Transactional 포함
//@DataJpaTest

@Slf4j
@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void save() {
        Member member = new Member("RepositoryTest", "RepositoryTest123","RepositoryTestNickName");

        Member saveMember = memberRepository.save(member);

        System.out.println("saveMember = " + saveMember);
        System.out.println("member = " + member);

        assertThat(saveMember.getId()).isEqualTo(member.getId());
        assertThat(saveMember.getUserId()).isEqualTo(member.getUserId());
        assertThat(saveMember.getUserPw()).isEqualTo(member.getUserPw());

    }

    @Test
    void findById() {
    }
}