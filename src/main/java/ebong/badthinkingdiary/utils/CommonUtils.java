package ebong.badthinkingdiary.utils;

import ebong.badthinkingdiary.domain.RoleList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Component
public class CommonUtils {

    /**
     * 일기 농도 계산<br>
     * 7일동안 옅어지는 일기의 농도 계산<br>
     * 농도 : 100%(1일) 85 70 55 40 25 5(7일) / 8일부터 일기 삭제 처리됨
     * @param diaryDay
     * @return opacity
     */
    public int getOpacity(LocalDateTime diaryDay){
        long elapsedTime = Duration.between( diaryDay, LocalDateTime.now() ).getSeconds();
        int secondsOfDay = 60 * 60 * 24; // sec : 1일 86400 ( 60 X 60 X 24 X 1 )

        for (int i = 0; i < 7; i++) {
            if (elapsedTime >= i && elapsedTime < secondsOfDay * (i + 1)) {
                return 100 - (i * 15);
            }
        }
        return 0; // 매일 0시 스케줄링으로 지워주긴 할거지만 혹시 남아있는 data가 있다면 0으로 처리
    }

    /**
     * BeanValidation Error 리턴
     * @param bindingResult
     */
    public void returnError(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.debug("bindingResult has Errors = {}", bindingResult);
            throw new IllegalArgumentException("bindingResult has Errors");
        }
    }

    /**
     *
     * @param loginId
     */
    public void validationRole(String loginId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(RoleList.ADMIN.name()));

        //@TODOnow 조건 맞는지 체크
        if (!userId.equals(loginId) && !isAdmin) {
            throw new IllegalArgumentException("Login User != argument"); //@TODOnow 메세지 수정
        }
    }

}
