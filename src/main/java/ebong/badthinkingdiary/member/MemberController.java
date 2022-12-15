package ebong.badthinkingdiary.member;

import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.dto.MemberSaveDTO;
import ebong.badthinkingdiary.dto.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/save")
    public ResponseDTO save(@Validated @RequestBody MemberSaveDTO saveDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.debug("errors={}", bindingResult);
            throw new IllegalArgumentException("bindingResult has Errors");
        }

        Member member = memberSaveDtoToMember(saveDto);
        memberService.save(member);

        return new ResponseDTO(HttpStatus.OK, true, "Sign up complete", member);
    }

    public Member memberSaveDtoToMember(MemberSaveDTO saveDto){
        return Member.builder()
                .userId(saveDto.getUserId())
                .userPw(saveDto.getUserPw())
                .nickName(saveDto.getNickName())
                .phoneNumber(saveDto.getPhoneNumber())
                .birthDay(saveDto.getBirthDay())
                .build();
    }

    /**
     * Member 조회
     * @param id
     * @return Member
     */
    @GetMapping("/findById/{id}")
    public ResponseDTO findById(@PathVariable Long id) {

        try {
            return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), memberService.findById(id));
        }catch (NoSuchElementException e) {
            throw e;
        }
    }

}
