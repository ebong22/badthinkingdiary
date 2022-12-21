package ebong.badthinkingdiary.utils;

import ebong.badthinkingdiary.apis.diaryprivate.DiaryPrivateService;
import ebong.badthinkingdiary.apis.diaryprivate.DiaryPrivateServiceImpl;
import ebong.badthinkingdiary.apis.diarypublic.DiaryPublicService;
import ebong.badthinkingdiary.domain.DiaryPrivate;
import ebong.badthinkingdiary.domain.DiaryPublic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulingUtils {

    private final DiaryPublicService diaryPublicService;
    private final DiaryPrivateService diaryPrivateService;

    /**
     *  비동기 구현 원하는 메소드에는
     *  @Async 어노테이션 붙여주기
     *
     *  cron = 초 분 시 일 월 요일
     */

    /**
     * DiaryPrivate 삭제 대상 삭제 : 작성 이후 7일 지난 일기 (8일째 되는 DATA부터 삭제)
     * 매일 0시
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Async
    public void deleteDiaryPrivate(){
        log.debug("[deleteDiaryPrivate] Scheduling start ===============");

        List<DiaryPrivate> deleteList = diaryPrivateService.findByDiaryDayLessThan(getStartDate());

        if( deleteList.size() > 0){
            for (DiaryPrivate diary : deleteList) {
                log.debug("delete diary id = {}, title= {}", diary.getId(), diary.getTitle());
                diaryPrivateService.deleteById(diary.getId());
            }
        }
        log.debug("[deleteDiaryPrivate] Scheduling end ===============");
    }


    /**
     * DiaryPublic 삭제 대상 삭제 : 작성 이후 7일 지난 일기 (8일째 되는 DATA부터 삭제)
     * 매일 0시
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Async
    public void deleteDiaryPublic(){
        log.debug("[deleteDiaryPublic] Scheduling start ===============");

        List<DiaryPublic> deleteList = diaryPublicService.findByDiaryDayLessThan(getStartDate());

        if( deleteList.size() > 0){
            for (DiaryPublic diary : deleteList) {
                log.debug("delete diary id = {}, title= {}", diary.getId(), diary.getTitle());
                diaryPublicService.deleteById(diary.getId());
            }
        }
        log.debug("[deleteDiaryPublic] Scheduling end ===============");
    }


    /**
     * Diary 삭제 기준일 계산 ( 6일 전 00:00 )
     * @return diary delete date
     */
    private LocalDateTime getStartDate() {
        return LocalDate.now().minusDays(6).atStartOfDay();
    }

}
