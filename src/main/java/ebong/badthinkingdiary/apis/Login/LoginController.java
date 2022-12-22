package ebong.badthinkingdiary.apis.Login;

import ebong.badthinkingdiary.apis.member.MemberService;
import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.dto.LoginDTO;
import ebong.badthinkingdiary.dto.ResponseDTO;
import ebong.badthinkingdiary.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
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

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody LoginDTO loginDTO) {
        log.debug("LOGIN MEMBER = {}", loginDTO.toString());

        // 임시 : USER 권한으로 테스트 @TODO 추후 삭제
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        //임시 end

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUserId(), loginDTO.getUserPw());
            Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            String jwtToken = jwtTokenProvider.createToken(authenticate, roles);

            return new ResponseDTO(HttpStatus.OK, true, "login succeess", jwtToken);

        } catch (BadCredentialsException e) {
            throw e;

        } catch (Exception e) {
            log.error("[ LOGIN ERROR ]", e);
            // @TODO now : throw exception 처리하여 controller advice 에서 exception handler 로 받아서 처리
            return new ResponseDTO(HttpStatus.BAD_REQUEST, false, "login fail", null);
        }

    }
}
