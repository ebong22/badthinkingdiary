package ebong.badthinkingdiary.apis.diarypublic;

import ebong.badthinkingdiary.domain.DiaryPublic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class DiaryPublicServiceTest {

    @Autowired
    DiaryPublicService diaryPublicService;

    @Test
    void save() {
        DiaryPublic diaryPublic = makeMockDiary(LocalDateTime.now());

        DiaryPublic saveDiary = diaryPublicService.save(diaryPublic);

        assertThat(diaryPublic.getId()).isEqualTo(saveDiary.getId());
    }

    @Test
    void delete() {
        DiaryPublic diaryPublic = makeMockDiary(LocalDateTime.now());
        DiaryPublic saveDiary = diaryPublicService.save(diaryPublic);

        diaryPublicService.delete(diaryPublic.getId());

        assertThatThrownBy(() -> diaryPublicService.findById(diaryPublic.getId()))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void findById() {
        DiaryPublic diaryPublic = makeMockDiary(LocalDateTime.now());
        DiaryPublic saveDiary = diaryPublicService.save(diaryPublic);

        assertThat(diaryPublicService.findById(diaryPublic.getId()))
                .isEqualTo(diaryPublic);
    }

    @Test
    void findAll() {
        DiaryPublic diaryPublic1 = makeMockDiary(LocalDateTime.now());
        DiaryPublic saveDiary1 = diaryPublicService.save(diaryPublic1);

        DiaryPublic diaryPublic2 = makeMockDiary(LocalDateTime.now());
        DiaryPublic saveDiary2 = diaryPublicService.save(diaryPublic2);

        DiaryPublic diaryPublic3 = makeMockDiary(LocalDateTime.now());
        DiaryPublic saveDiary3 = diaryPublicService.save(diaryPublic3);

        assertThat(diaryPublicService.findAll())
                .contains(diaryPublic1, diaryPublic2, diaryPublic3);
    }

    @Test
    void findByDiaryDayLessThan() {
        DiaryPublic diaryPublic1 = makeMockDiary(LocalDateTime.of(2023, 1, 19, 23,  0));
        DiaryPublic saveDiary1 = diaryPublicService.save(diaryPublic1);

        DiaryPublic diaryPublic2 = makeMockDiary(LocalDateTime.of(2023, 1, 20, 23,  0));
        DiaryPublic saveDiary2 = diaryPublicService.save(diaryPublic2);

        DiaryPublic diaryPublic3 = makeMockDiary(LocalDateTime.of(2023, 1, 21, 23,  0));
        DiaryPublic saveDiary3 = diaryPublicService.save(diaryPublic3);

        DiaryPublic diaryPublic4 = makeMockDiary(LocalDateTime.of(2023, 1, 22, 23,  0));
        DiaryPublic saveDiary4 = diaryPublicService.save(diaryPublic4);

        List<DiaryPublic> diaryPublics = diaryPublicService.findByDiaryDayLessThan(LocalDateTime.of(2023, 1, 21, 23, 0));

        assertThat(diaryPublics).contains(diaryPublic1, diaryPublic2);
        assertThat(diaryPublics).doesNotContain(diaryPublic3, diaryPublic4);
    }


    private DiaryPublic makeMockDiary(LocalDateTime date) {
        DiaryPublic diary = DiaryPublic.builder()
                .title("test")
                .contents("test")
                .diaryDay(date)
                .build();

        return diaryPublicService.save(diary);
    }
}