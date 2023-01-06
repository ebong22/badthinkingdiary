package ebong.badthinkingdiary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class MemberDTO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "로그인 아이디")
    private String userId;

    @Schema(description = "닉네임")
    private String nickName;

    @Schema(description = "전화번호")
    private String phoneNumber;

    @Schema(description = "생년월일")
    private LocalDate birthDay;

    @Schema(description = "권한")
    private Object authority;

    @Builder
    public MemberDTO(Long id, String userId, String nickName, String phoneNumber, LocalDate birthDay, Object authority) {
        this.id = id;
        this.userId = userId;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.authority = authority;
    }
}
