package ebong.badthinkingdiary.diaryprivate;

import ebong.badthinkingdiary.apis.diaryprivate.DiaryPrivateService;
import ebong.badthinkingdiary.domain.DiaryListIcon;
import ebong.badthinkingdiary.domain.DiaryPrivate;
import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.apis.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class DiaryPrivateServiceImplTest {

    @Autowired
    private DiaryPrivateService diaryPrivateService;

    @Autowired
    private MemberService memberService;


    @Test
    void findByMemberIdTest() {
        Member member = mockMember();
        diaryPrivateService.save(mockDiaryPrivate(member));
        diaryPrivateService.save(mockDiaryPrivate(member));

        List<DiaryPrivate> diarys = diaryPrivateService.findByMemberId(member.getId());
//        System.out.println("diarys =================" + diarys.toString());
//
//        for (DiaryPrivate diary : diarys) {
//            System.out.println("diary =================" + diary.toString());
//        }

        assertThat(diarys.size()).isEqualTo(2);
    }

    private Member mockMember(){
        return memberService.save(new Member("ServiceTest", "ServiceTest123","ServiceTestNickName"));
    }

    private DiaryPrivate mockDiaryPrivate(Member member){
        return new DiaryPrivate(member, "testTitle", "testContents", LocalDateTime.now(), DiaryListIcon.ICON_1);
    }
}