package ebong.badthinkingdiary.member;

import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.domain.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service("memberService")
@AllArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Long save(Member member) {
        Member saveMember = memberRepository.save(member);
        return saveMember.getId();
    }

    @Override
    public Member findById(Long id) {
        Optional<Member> memberWrapper = memberRepository.findById(id);
        if (memberWrapper.isPresent()) {
            return memberWrapper.get();
        }
        log.debug("can not find Member");
        throw new NoSuchElementException("not exist member");
    }

    @Override
    public List<Member> findAll(){
        return memberRepository.findAll();
    }
}
