package ebong.badthinkingdiary.dto;

import ebong.badthinkingdiary.domain.RoleList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleDTO {
    @NotNull
    private RoleList name;
}
