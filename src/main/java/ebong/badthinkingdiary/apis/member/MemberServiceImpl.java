package ebong.badthinkingdiary.apis.member;

import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.domain.MemberRepository;
import ebong.badthinkingdiary.dto.MemberUpdateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service("memberService")
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Member save(Member member) {
        member.setSignUpData();
        return memberRepository.save(member);
    }


    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id)
                            .orElseThrow(() -> new NoSuchElementException("not exist member"));

//        Optional<Member> memberWrapper = memberRepository.findById(id);
//        if (memberWrapper.isPresent()) {
//            return memberWrapper.get();
//        }
//        log.debug("can not find Member");
//        throw new NoSuchElementException("not exist member");
    }


    @Override
    public List<Member> findAll(){
        return memberRepository.findAll();
    }


    @Override
    public Member update(MemberUpdateDTO updateDTO) {
        Member findMember = findById(updateDTO.getId());
        //@TODO now : null로 넘어오는 파라미터도 있을텐데 괜찮은가. getXX할 떄 nullpoint 안나나 흠
        findMember.memberUpdate(updateDTO.getUserPw(), updateDTO.getNickName());

        return findMember;
    }


    @Override
    public void delete(Long id){
        memberRepository.deleteById(id);
    }
}
