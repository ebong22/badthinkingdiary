package ebong.badthinkingdiary.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DiaryPublic extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @TODO now :익명 공개 일기에는 Member 정보 안넣을것임. => 이 부분에 대해 생각해보기 ( 넣는게 맞는지 아닌지, 진짜 익명으로 할지 보이기만 익명으로 할지 )
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="member_id")
//    private Member member;

    private String title;

    @Lob
    private String contents;

    private LocalDateTime diaryDay;

    @Enumerated(EnumType.STRING)
    private DiaryListIcon icon;

    @Builder
    public DiaryPublic(String title, String contents, LocalDateTime diaryDay, DiaryListIcon icon) {
        this.title = title;
        this.contents = contents;
        this.diaryDay = diaryDay;
        this.icon = icon;
    }
}
