package ebong.badthinkingdiary.apis.member;

import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.dto.MemberSaveDTO;
import ebong.badthinkingdiary.dto.MemberUpdateDTO;
import ebong.badthinkingdiary.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@Tag(name = "Member 회원", description = "회원 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;


    /**
     * 회원 가입
     * @param saveDto
     * @param bindingResult
     * @return ResponseDTO
     */
    @Operation(summary = "회원가입", description = "회원가입")
    @PostMapping("/save")
    public ResponseDTO save(@Validated @RequestBody MemberSaveDTO saveDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.debug("bindingResult has Errors = {}", bindingResult);
            throw new IllegalArgumentException("bindingResult has Errors");
        }

        Member member = memberService.save(memberSaveDtoToMember(saveDto));
        return new ResponseDTO(HttpStatus.OK, true, "Sign up complete", member);
    }


    /**
     * Member 조회
     * @param id
     * @return ResponseDTO
     */
    @Operation(summary = "회원 조회", description = "회원 id를 통해 단일 회원을 조회함")
    @GetMapping("/find/{id}")
    public ResponseDTO findById(@PathVariable Long id) {
        return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), memberService.findById(id));
    }


    /**
     * Member 수정<br>
     * 수정가능 항목 : userPw, nickName
     * @param id
     * @param updateDTO
     * @return ResponseDTO
     */
    @Operation(summary = "회원 수정", description = "회원 정보를 수정( 수정 가능 항목 : userPw, nickName )")
    @PostMapping("/update/{id}")
    public ResponseDTO update(@PathVariable Long id, @RequestBody MemberUpdateDTO updateDTO) {
        Member updateMember = memberService.update(updateDTO);
        return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), updateMember);
    }


    /**
     * Member 삭제
     * @param id
     * @return ResponseDTO
     */
    @Operation(summary = "회원 삭제", description = "회원 id를 통해 단일 회원을 삭제함")
    @GetMapping("/delete/{id}")
    public ResponseDTO delete(@PathVariable Long id) {
        memberService.delete(id);
        return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), null);
    }


    /**
     * MemberSaveDto to Member
     * @param saveDto
     * @return Member
     */
    public Member memberSaveDtoToMember(MemberSaveDTO saveDto){
        return Member.builder()
                .userId(saveDto.getUserId())
                .userPw(saveDto.getUserPw())
                .nickName(saveDto.getNickName())
                .phoneNumber(saveDto.getPhoneNumber())
                .birthDay(saveDto.getBirthDay())
                .build();
    }
}
