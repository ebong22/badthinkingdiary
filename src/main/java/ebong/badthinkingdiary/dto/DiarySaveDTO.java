package ebong.badthinkingdiary.dto;

import ebong.badthinkingdiary.domain.DiaryListIcon;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class DiarySaveDTO {

    @Schema(description = "일기 제목")
    @NotNull
    private String title;

    @Schema(description = "일기 내용")
    @NotNull
    private String contents;

    @Schema(description = "일기 일자")
    @NotNull
    private LocalDateTime diaryDay;

    @Schema(description = "목록 아이콘")
    @NotNull
    private DiaryListIcon icon;

}
