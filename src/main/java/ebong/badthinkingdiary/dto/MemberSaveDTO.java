package ebong.badthinkingdiary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

// @TODO 리펙토링 : 추후에 VALIDATION 공통으로 처리하는 클래스 만들어보기
@Getter
public class MemberSaveDTO {

    @Schema(description = "로그인 아이디")
    @NotBlank
    private String userId;

    @Schema(description = "로그인 비밀번호")
    @NotBlank
//    @Pattern(regexp="[a-zA-Z1-9]{6,12}$", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
    private String userPw;

    @Schema(description = "닉네임")
    @NotBlank
    private String nickName;

    @Schema(description = "전화번호")
    @NotBlank
//    @Pattern(regexp = "^[0-9]$", message = "숫자만 입력 가능합니다.")
    private String phoneNumber;

    @Schema(description = "생년월일")
    @NotNull
    private LocalDate birthDay;

}
