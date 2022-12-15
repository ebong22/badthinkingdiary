package ebong.badthinkingdiary.dto;

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

    @NotBlank
    private String userId;

    @NotBlank
//    @Pattern(regexp="[a-zA-Z1-9]{6,12}$", message = "비밀번호는 영어와 숫자로 포함해서 6~12자리 이내로 입력해주세요.")
    private String userPw;

    @NotBlank
    private String nickName;

    @NotBlank
//    @Pattern(regexp = "^[0-9]$", message = "숫자만 입력 가능합니다.")
    private String phoneNumber;

    @NotNull
    private LocalDate birthDay;

}
