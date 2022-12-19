package ebong.badthinkingdiary.apis.diaryprivate;

import ebong.badthinkingdiary.domain.DiaryPrivate;

import java.util.List;

public interface DiaryPrivateService {
    DiaryPrivate save(DiaryPrivate diaryPrivate);

    DiaryPrivate findById(Long id);

    List<DiaryPrivate> findByMemberId(Long memberId);

    void deleteById(Long id);
}
