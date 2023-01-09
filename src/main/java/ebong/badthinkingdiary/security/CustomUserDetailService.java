package ebong.badthinkingdiary.security;

import ebong.badthinkingdiary.apis.member.MemberService;
import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.domain.MemberRepository;
import ebong.badthinkingdiary.domain.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("userDetailService")
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String usrId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(usrId)
                .orElseThrow(() -> new UsernameNotFoundException("not exist member : [" + usrId + "]"));

        List<MemberRole> memberRoles = memberService.getMemberRole(member.getId());
        return new CustomUserDetails(member, memberService.memberRolesToList(memberRoles));
    }

}
