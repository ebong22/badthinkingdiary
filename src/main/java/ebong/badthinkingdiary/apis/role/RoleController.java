package ebong.badthinkingdiary.apis.role;

import ebong.badthinkingdiary.apis.member.MemberService;
import ebong.badthinkingdiary.domain.*;
import ebong.badthinkingdiary.dto.MemberRoleDTO;
import ebong.badthinkingdiary.dto.ResponseDTO;
import ebong.badthinkingdiary.dto.RoleDTO;
import ebong.badthinkingdiary.utils.CommonUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Tag(name = "권한관리", description = "권한관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("role")
public class RoleController {

    private final RoleService roleService;
    private final MemberService memberService;
    private final CommonUtils commonUtils;

    // @TODO 1227 진행중 : ROLE 관련 메소드 및 entity 만들기 -> 다하면 시큐리티에 ROLE 적용
    // @TODO 리팩토링 지금 ENUM / DB 둘다로 해놨는데 정확히 뭐로할지 정할 필요가 있음


    /**
     * member에 권한 부여
     * @param roleDTO
     * @param bindingResult
     * @return ResponseDTO
     */
    @PostMapping("/save/member-role")
    public ResponseDTO save(@Validated @RequestBody MemberRoleDTO roleDTO, BindingResult bindingResult) {
        commonUtils.returnError(bindingResult);

        log.debug("MemberRoleDTO = {}", roleDTO.toString());
        List<MemberRole> memberRoles = memberRoleDtoToMemberRole(roleDTO);
        for (MemberRole m : memberRoles) {

            roleService.saveMemberRole(m);
        }

        return new ResponseDTO(HttpStatus.OK, true, "save complete", null);
    }


    /**
     * 권한 생성
     * @param roleDTO
     * @param bindingResult
     * @return
     */
    @PostMapping("/save/new-role")
    public ResponseDTO createRole(@Validated @RequestBody RoleDTO roleDTO, BindingResult bindingResult) {
        commonUtils.returnError(bindingResult);

        Role role = new Role(roleDTO.getName());
        roleService.save(role);

        return new ResponseDTO(HttpStatus.OK, true, "role create complete", null);
    }


    /**
     * MemberRoleDto to MemberRole
     * @param roleDTO
     * @return MemberRole List
     */
    public List<MemberRole> memberRoleDtoToMemberRole(MemberRoleDTO roleDTO){
        List<RoleList> roles = roleDTO.getRoles();

        if( !roles.isEmpty()){
            List<MemberRole> memberRoles = new ArrayList<>();
            Member member = memberService.findById(roleDTO.getUserId());

            for (RoleList r : roles) {
                memberRoles.add ( new MemberRole(member, roleService.findByName(r)) );
            }
            return memberRoles;
        }
        throw new NullPointerException("roleDTO.getRoles() is null");
    }
}
