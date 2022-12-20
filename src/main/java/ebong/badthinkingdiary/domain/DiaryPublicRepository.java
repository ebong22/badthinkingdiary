package ebong.badthinkingdiary.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DiaryPublicRepository extends JpaRepository<DiaryPublic, Long> {

}
