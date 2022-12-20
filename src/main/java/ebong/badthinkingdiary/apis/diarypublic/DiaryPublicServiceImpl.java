package ebong.badthinkingdiary.apis.diarypublic;

import ebong.badthinkingdiary.domain.DiaryPublic;
import ebong.badthinkingdiary.domain.DiaryPublicRepository;
import ebong.badthinkingdiary.utils.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service("diaryPublicService")
@AllArgsConstructor
@Transactional
public class DiaryPublicServiceImpl implements DiaryPublicService {

    private final DiaryPublicRepository diaryPublicRepository;
    private final CommonUtils commonUtils;


    @Override
    public DiaryPublic save(DiaryPublic diaryPublic){
        return diaryPublicRepository.save(diaryPublic);
    }


    @Override
    public DiaryPublic findById(Long id) {
        return diaryPublicRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("not exitst diary"));
    }


    @Override
    public List<DiaryPublic> findAll(){
        return diaryPublicRepository.findAll();
    }


    // @Todo now :여기서 안쓰고 스케줄러에서 바로 diaryPublicRepository 가져다가 써도 되는데 그건 생각해보기
    @Override
    public void deleteById(Long id) {
        diaryPublicRepository.deleteById(id);
    }

}
