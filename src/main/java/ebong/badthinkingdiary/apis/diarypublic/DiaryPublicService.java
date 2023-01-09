package ebong.badthinkingdiary.apis.diarypublic;

import ebong.badthinkingdiary.domain.DiaryPublic;

import java.time.LocalDateTime;
import java.util.List;

public interface DiaryPublicService {

    /**
     * DiaryPublic 저장
     * @param diaryPublic
     * @return
     */
    DiaryPublic save(DiaryPublic diaryPublic);

    // @Todonow :여기서 안쓰고 스케줄러에서 바로 diaryPublicRepository 가져다가 써도 되는데 그건 생각해보기
    /**
     * DiaryPublic 삭제
     * @param id
     */
    void delete(Long id);

    /**
     * DiaryPublic 단건조회<br>
     * DiaryPublic id를 통한 단건조회
     * @param id
     * @return DiaryPublic
     */
    DiaryPublic findById(Long id);

    /**
     * DiaryPublic 전체조회
     * @return DiaryPublic List
     */
    List<DiaryPublic> findAll();

    /**
     * 특정 날짜 이전에 작성된 DiaryPublic 조회
     * @param date
     * @return DiaryPublic List
     */
    List<DiaryPublic> findByDiaryDayLessThan(LocalDateTime date);

}
