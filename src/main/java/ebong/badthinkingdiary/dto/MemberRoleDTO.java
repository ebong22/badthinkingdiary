package ebong.badthinkingdiary.dto;

import ebong.badthinkingdiary.domain.RoleList;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberRoleDTO {

    @NotNull
    private Long userId;

    // @TODO 리팩토링 : 추후 권한설정 부분 여러개 선택해서 한번에 담을 수 있는 형태도 생각해서 List로 받음
    // -> 이게 과연 옳은 걸지는 생각해보고 아니다 싶으면그냥 RoleList로 리팩토링
    @NotEmpty
    private List<RoleList> roles;

}
