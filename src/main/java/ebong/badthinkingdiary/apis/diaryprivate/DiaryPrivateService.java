package ebong.badthinkingdiary.apis.diaryprivate;

import ebong.badthinkingdiary.domain.DiaryPrivate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

public interface DiaryPrivateService {

    /**
     * DiaryPrivate 저장
     * @param diaryPrivate
     * @return
     */
    DiaryPrivate save(DiaryPrivate diaryPrivate);

    /**
     * DiaryPrivate 단건조회<br>
     * DiaryPrivate id를 통한 단건조회
     * @param id
     * @return DiaryPrivate
     * @throws NoSuchElementException
     */
    DiaryPrivate find(Long id);

    /**
     * DiaryPrivate 삭제
     * @param id
     */
    void delete(Long id);

    /**
     * 특정 날짜 이전에 작성된 DiaryPrivate 조회
     * @param date
     * @return DiaryPrivate
     */
    List<DiaryPrivate> findByDiaryDayLessThan(LocalDateTime date);

    /**
     * DiaryPrivate 다건 조회<br>
     * 특정 Member가 작성한 모든 DiaryPrivate 조회
     * @param memberId
     * @return DiaryPrivate List
     */
    List<DiaryPrivate> findByMemberId(Long memberId);

}
