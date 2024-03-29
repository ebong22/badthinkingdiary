package ebong.badthinkingdiary.apis.diaryprivate;

import ebong.badthinkingdiary.domain.DiaryPrivate;
import ebong.badthinkingdiary.domain.DiaryPrivateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service("diaryPrivateService")
@RequiredArgsConstructor
@Transactional
public class DiaryPrivateServiceImpl implements DiaryPrivateService{

    private final DiaryPrivateRepository diaryPrivateRepository;

    @Override
    public DiaryPrivate save(DiaryPrivate diaryPrivate){
        return diaryPrivateRepository.save(diaryPrivate);
    }


    @Override
    public DiaryPrivate findById(Long id) {
        return diaryPrivateRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("not exist diary"));
    }


    @Override
    public List<DiaryPrivate> findByDiaryDayLessThan(LocalDateTime date){
        return diaryPrivateRepository.findByDiaryDayLessThan(date);
    }


    @Override
    public List<DiaryPrivate> findByMemberId(Long memberId) {
        return diaryPrivateRepository.findByMemberId(memberId);
    }


    @Override
    public void deleteById(Long id) {
        diaryPrivateRepository.deleteById(id);
    }

}
