package ebong.badthinkingdiary.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DiaryPrivate extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private String title;

    @Lob
    private String contents;

    private LocalDateTime diaryDay;

    @Enumerated(EnumType.STRING)
    private DiaryListIcon icon;

    @Builder
    public DiaryPrivate(Member member, String title, String contents, LocalDateTime diaryDay, DiaryListIcon icon) {
        this.member = member;
        this.title = title;
        this.contents = contents;
        this.diaryDay = diaryDay;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "DiaryPrivate{" +
                "id=" + id +
                ", member=" + member.toString() +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", diaryDay=" + diaryDay +
                ", icon=" + icon +
                '}';
    }
}
