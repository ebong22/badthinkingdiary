package ebong.badthinkingdiary.apis.diarypublic;

import ebong.badthinkingdiary.domain.DiaryPublic;

import java.util.List;

public interface DiaryPublicService {
    DiaryPublic save(DiaryPublic diaryPublic);

    DiaryPublic findById(Long id);

    List<DiaryPublic> findAll();

    // @Todo now :여기서 안쓰고 스케줄러에서 바로 diaryPublicRepository 가져다가 써도 되는데 그건 생각해보기
    void deleteById(Long id);
}
