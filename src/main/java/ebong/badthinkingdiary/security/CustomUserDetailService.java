package ebong.badthinkingdiary.security;

import ebong.badthinkingdiary.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("userDetailService")
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String usrId) throws UsernameNotFoundException {
        return memberRepository.findByUserId(usrId)
                .orElseThrow( () -> new UsernameNotFoundException("not exist member : [" + usrId + "]") );
    }

}
