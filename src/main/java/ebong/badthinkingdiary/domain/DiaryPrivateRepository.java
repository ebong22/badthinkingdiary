package ebong.badthinkingdiary.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DiaryPrivateRepository extends JpaRepository<DiaryPrivate, Long> {

    List<DiaryPrivate> findByMemberId(Long id);

    List<DiaryPrivate> findByDiaryDayLessThan(LocalDateTime date);
}
