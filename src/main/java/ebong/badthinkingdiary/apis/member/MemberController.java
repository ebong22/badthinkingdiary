package ebong.badthinkingdiary.apis.member;

import ebong.badthinkingdiary.apis.role.RoleService;
import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.domain.MemberRole;
import ebong.badthinkingdiary.dto.MemberDTO;
import ebong.badthinkingdiary.dto.MemberSaveDTO;
import ebong.badthinkingdiary.dto.MemberUpdateDTO;
import ebong.badthinkingdiary.dto.ResponseDTO;
import ebong.badthinkingdiary.utils.CommonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @TODOnow : 전체적으로 response data에 member 전체를 보내주는데, 이렇게 말고 보여줄 값만 dto로 만들어서 전달 필요
 * memberToReturnDto 메소드도 만들어서 일괄로 변경해주고
 */

@Slf4j
@Tag(name = "Member 회원", description = "회원 관련 api 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final CommonUtils commonUtils;


    /**
     * 회원 가입
     * @param saveDto
     * @param bindingResult
     * @return ResponseDTO
     */
    @Operation(summary = "회원가입", description = "회원가입")
    @PostMapping("/save")
    public ResponseDTO save(@Validated @RequestBody MemberSaveDTO saveDto, BindingResult bindingResult) {
        commonUtils.returnError(bindingResult);

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
        MemberDTO returnMember = memberToMemberDto(memberService.find(id), memberService.getMemberRole(id));
        return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), returnMember);
    }

    /**
     * Member 수정<br>
     * 수정가능 항목 : userPw, nickName
//     * @param id
     * @param updateDTO
     * @return ResponseDTO
     */
    @Operation(summary = "회원 수정", description = "회원 정보를 수정( 수정 가능 항목 : userPw, nickName )")
//    @PostMapping("/update/{id}")
    @PostMapping("/update")
    public ResponseDTO update(/*@PathVariable Long id,*/ @RequestBody MemberUpdateDTO updateDTO) {
        /**
         * @TODOnow validation 방식 바꾸기
         * 토큰에서 userid 가져오고
         * pathvariable에서 요청한 id로 member를 찾아서
         * 두 userId가 다르면 false (admin) 예외
         */
        commonUtils.validationRole( memberService.find(updateDTO.getId()).getUserId() ); // @TODOnow userRole&id validation 필요한 부분에 적용

        String updatePw = updateDTO.getUserPw();
        if (updatePw != null && !updatePw.isEmpty()) {
            updateDTO.setUserPw(encodingPassword(updatePw));
        }
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
                .userPw(encodingPassword(saveDto.getUserPw())) // 비밀번호 암호화
                .nickName(saveDto.getNickName())
                .phoneNumber(saveDto.getPhoneNumber())
                .birthDay(saveDto.getBirthDay())
                .build();
    }

    /**
     * Member to MemberDto
     * @param member
     * @param roleList
     * @return MemberDTO
     */
    public MemberDTO memberToMemberDto(Member member, List<MemberRole> roleList) {
        return MemberDTO.builder()
                    .id(member.getId())
                    .userId(member.getUserId())
                    .nickName(member.getNickName())
                    .phoneNumber(member.getPhoneNumber())
                    .birthDay(member.getBirthDay())
                    .authority(roleList)
                    .build();
    }

    /**
     * 비밀번호 암호화
     * @param userPw
     * @return
     */
    private String encodingPassword(String userPw ){
        return passwordEncoder.encode(userPw);
    }
}
