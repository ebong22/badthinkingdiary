package ebong.badthinkingdiary.apis.Login;

import ebong.badthinkingdiary.apis.member.MemberService;
import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.domain.MemberRole;
import ebong.badthinkingdiary.domain.RoleList;
import ebong.badthinkingdiary.dto.LoginDTO;
import ebong.badthinkingdiary.dto.ResponseDTO;
import ebong.badthinkingdiary.security.JwtTokenProvider;
import ebong.badthinkingdiary.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final CommonUtils commonUtils;

    /**
     * 로그인
     * @param loginDTO
     * @return ResponseDTO
     */
    @PostMapping("/login")
    public ResponseDTO login(@Validated @RequestBody LoginDTO loginDTO, BindingResult bindingResult) {
        commonUtils.returnError(bindingResult);
        log.debug("LOGIN MEMBER = {}", loginDTO.toString());

        List<RoleList> roles = getRoleLists(loginDTO.getUserId());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUserId(), loginDTO.getUserPw());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String jwtToken = jwtTokenProvider.createToken(authenticate, roles);

        return new ResponseDTO(HttpStatus.OK, true, "login succeess", jwtToken);

    }


    /**
     * userId를 통해 권한 조회
     * @param userId
     * @return member Role list
     */
    private List<RoleList> getRoleLists(String userId) {

        List<MemberRole> memberRoles =  memberService.getMemberRole(memberService.findByUserId(userId).getId());
        List<RoleList> roles = new ArrayList<>();

        for (MemberRole m : memberRoles) {
            roles.add(m.getRole().getName());
        }
        return roles;
    }
}
