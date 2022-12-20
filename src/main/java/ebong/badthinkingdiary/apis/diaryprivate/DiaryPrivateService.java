package ebong.badthinkingdiary.apis.diaryprivate;

import ebong.badthinkingdiary.domain.DiaryPrivate;

import java.time.LocalDateTime;
import java.util.List;

public interface DiaryPrivateService {
    DiaryPrivate save(DiaryPrivate diaryPrivate);

    DiaryPrivate findById(Long id);

    List<DiaryPrivate> findByDiaryDayLessThan(LocalDateTime date);

    List<DiaryPrivate> findByMemberId(Long memberId);

    void deleteById(Long id);
}
