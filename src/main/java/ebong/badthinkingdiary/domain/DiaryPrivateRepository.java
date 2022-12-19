package ebong.badthinkingdiary.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryPrivateRepository extends JpaRepository<DiaryPrivate, Long> {

    List<DiaryPrivate> findByMemberId(Long id);
}
