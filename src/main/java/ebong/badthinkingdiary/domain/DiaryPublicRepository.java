package ebong.badthinkingdiary.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DiaryPublicRepository extends JpaRepository<DiaryPublic, Long> {

    List<DiaryPublic> findByDiaryDayLessThan(LocalDateTime date);

}
