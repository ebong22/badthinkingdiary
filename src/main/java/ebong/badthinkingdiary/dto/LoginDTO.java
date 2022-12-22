package ebong.badthinkingdiary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
public class LoginDTO {

    @Schema(description = "로그인 아이디")
    @NotBlank
    private String userId;

    @Schema(description = "로그인 비밀번호")
    @NotBlank
    private String userPw;
}
