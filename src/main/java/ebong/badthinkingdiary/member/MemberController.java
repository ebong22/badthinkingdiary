package ebong.badthinkingdiary.member;

import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.dto.MemberSaveDTO;
import ebong.badthinkingdiary.dto.MemberUpdateDTO;
import ebong.badthinkingdiary.dto.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @TODO now : 전체적으로 response data에 member 전체를 보내주는데, 이렇게 말고 보여줄 값만 dto로 만들어서 전달 필요
 * memberToReturnDto 메소드도 만들어서 일괄로 변경해주고
 */
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

        Member member = memberService.save(memberSaveDtoToMember(saveDto));

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
    @GetMapping("/find/{id}")
    public ResponseDTO findById(@PathVariable Long id) {
        return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), memberService.findById(id));
    }

    
    @PostMapping("/update/{id}")
    public ResponseDTO update(@PathVariable Long id, @RequestBody MemberUpdateDTO updateDTO) {
        Member updateMember = memberService.update(updateDTO);

        return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), updateMember);
    }

    @GetMapping("/delete/{id}")
    public ResponseDTO delete(@PathVariable Long id) {
        memberService.delete(id);
        return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), null);
    }

}
