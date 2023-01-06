package ebong.badthinkingdiary.apis.Login;

import ebong.badthinkingdiary.apis.member.MemberService;
import ebong.badthinkingdiary.domain.MemberRole;
import ebong.badthinkingdiary.domain.RefreshToken;
import ebong.badthinkingdiary.domain.RoleList;
import ebong.badthinkingdiary.dto.LoginDTO;
import ebong.badthinkingdiary.dto.ResponseDTO;
import ebong.badthinkingdiary.dto.TokenDTO;
import ebong.badthinkingdiary.security.JwtTokenProvider;
import ebong.badthinkingdiary.utils.CommonUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Tag(name = "로그인", description = "로그인")
@RestController
@RequiredArgsConstructor
@RequestMapping
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider;
    private final LoginService loginService;
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

        TokenDTO token = loginService.createToken4Login(authenticate);

        return new ResponseDTO(HttpStatus.OK, true, "login success", token);

    }


    /**
     * refresh token 검증 및 token 재발급
     * @param refreshToken
     * @return
     */
    @PostMapping("/refresh")
    public ResponseDTO refreshToken(@RequestBody String refreshToken){

        RefreshToken token = loginService.findRefreshToken(refreshToken);

        if(token.getExpireDate().isBefore(Instant.now())){
            throw new IllegalArgumentException("expired refreshToken");
        }

        TokenDTO reissueToken = loginService.refresh(token);

        return new ResponseDTO(HttpStatus.OK, true, "refresh success", reissueToken);
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
