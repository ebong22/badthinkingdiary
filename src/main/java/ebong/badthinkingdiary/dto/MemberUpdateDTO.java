package ebong.badthinkingdiary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberUpdateDTO {

    @Schema(description = "id(PK)")
    @NotBlank
    private Long    id;

    @Schema(description = "로그인 아이디")
    private String  userId;

    @Schema(description = "로그인 비밀번호")
    private String  userPw;

    @Schema(description = "닉네임")
    private String  nickName;
}
