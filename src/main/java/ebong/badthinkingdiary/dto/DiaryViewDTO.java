package ebong.badthinkingdiary.dto;

import ebong.badthinkingdiary.domain.DiaryListIcon;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/*
 * 일기 보여주기용 dto
 * diary_ private / public 공통으로 사용
 * private에서 member정보 필요하면 따로 현재 로그인하고 있는 member 정보 가져다가 사용
 */
@Getter
public class DiaryViewDTO {

    @Schema(description = "일기 제목")
    private String title;

    @Schema(description = "일기 내용")
    private String contents;

    @Schema(description = "일기 일자")
    private LocalDateTime diaryDay;

    @Schema(description = "목록 아이콘")
    private DiaryListIcon icon;

    @Schema(description = "일기 투명도")
    private int opacity;

    @Builder
    public DiaryViewDTO(String title, String contents, LocalDateTime diaryDay, DiaryListIcon icon, int opacity) {
        this.title = title;
        this.contents = contents;
        this.diaryDay = diaryDay;
        this.icon = icon;
        this.opacity = opacity;
    }
}
