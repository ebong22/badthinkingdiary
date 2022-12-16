package ebong.badthinkingdiary.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberUpdateDTO {

    @NotBlank
    private Long    id;

    private String  userPw;

    private String  nickName;
}
