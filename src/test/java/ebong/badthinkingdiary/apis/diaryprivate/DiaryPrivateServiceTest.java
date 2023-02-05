package ebong.badthinkingdiary.apis.diaryprivate;

import ebong.badthinkingdiary.apis.member.MemberService;
import ebong.badthinkingdiary.apis.role.RoleService;
import ebong.badthinkingdiary.domain.DiaryPrivate;
import ebong.badthinkingdiary.domain.Member;
import ebong.badthinkingdiary.domain.MemberRole;
import ebong.badthinkingdiary.domain.RoleList;
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
class DiaryPrivateServiceTest {

    @Autowired
    DiaryPrivateService diaryPrivateService;
    @Autowired
    MemberService memberService;
    @Autowired
    RoleService roleService;


    @Test
    public void save() {
        Member member = makeMockUser();
        DiaryPrivate diaryPrivate = makeMockDiary(member, LocalDateTime.now());

        DiaryPrivate saveDiary = diaryPrivateService.save(diaryPrivate);

        assertThat(diaryPrivate.getId()).isEqualTo(saveDiary.getId());
    }

    @Test
    public void delete() {
        Member member = makeMockUser();
        DiaryPrivate diaryPrivate = makeMockDiary(member, LocalDateTime.now());
        DiaryPrivate saveDiary = diaryPrivateService.save(diaryPrivate);

        diaryPrivateService.delete(saveDiary.getId());

        assertThatThrownBy(() -> diaryPrivateService.find(saveDiary.getId()))
                            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void find() {
        Member member = makeMockUser();
        DiaryPrivate diaryPrivate = makeMockDiary(member, LocalDateTime.now());
        DiaryPrivate saveDiary = diaryPrivateService.save(diaryPrivate);

        assertThat(diaryPrivateService.find(diaryPrivate.getId()))
                .isEqualTo(diaryPrivate);
    }

    @Test
    public void findByDiaryDayLessThan() {
        Member member = makeMockUser();
        DiaryPrivate diaryPrivate1 = makeMockDiary(member, LocalDateTime.of(2023, 1, 19, 23,  0));
        DiaryPrivate saveDiary1 = diaryPrivateService.save(diaryPrivate1);

        DiaryPrivate diaryPrivate2 = makeMockDiary(member, LocalDateTime.of(2023, 1, 20, 23,  0));
        DiaryPrivate saveDiary2 = diaryPrivateService.save(diaryPrivate2);

        DiaryPrivate diaryPrivate3 = makeMockDiary(member, LocalDateTime.of(2023, 1, 21, 23,  0));
        DiaryPrivate saveDiary3 = diaryPrivateService.save(diaryPrivate3);

        DiaryPrivate diaryPrivate4 = makeMockDiary(member, LocalDateTime.of(2023, 1, 22, 23,  0));
        DiaryPrivate saveDiary4 = diaryPrivateService.save(diaryPrivate4);

        List<DiaryPrivate> diaryPrivates = diaryPrivateService.findByDiaryDayLessThan(LocalDateTime.of(2023, 1, 21, 23, 0));

        assertThat(diaryPrivates).contains(diaryPrivate1, diaryPrivate2);
        assertThat(diaryPrivates.contains(diaryPrivate3)).isEqualTo(false);
        assertThat(diaryPrivates.contains(diaryPrivate4)).isEqualTo(false);
    }

    @Test
    public void findByMemberId() {
        Member member = makeMockUser();
        DiaryPrivate diaryPrivate1 = makeMockDiary(member, LocalDateTime.of(2023, 1, 19, 23,  0));
        DiaryPrivate saveDiary1 = diaryPrivateService.save(diaryPrivate1);

        DiaryPrivate diaryPrivate2 = makeMockDiary(member, LocalDateTime.of(2023, 1, 20, 23,  0));
        DiaryPrivate saveDiary2 = diaryPrivateService.save(diaryPrivate2);

        DiaryPrivate diaryPrivate3 = makeMockDiary(member, LocalDateTime.of(2023, 1, 21, 23,  0));
        DiaryPrivate saveDiary3 = diaryPrivateService.save(diaryPrivate3);

        assertThat(diaryPrivateService.findByMemberId(member.getId()))
                .contains(diaryPrivate1, diaryPrivate2, diaryPrivate3);
    }


    // @TODONOW 여러 메소드 사용하는 테스트는 새로운 테스트 클래스로 분리하여 해보기
//    @Test
//    public void findByIdRoleUser() {
//        /**
//         * 회원가입
//         * Role 부여 (USER)
//         * 해당 아이디로 로그인
//         * 다이어리 작성
//         * find
//         */
//    }

//    @Test
//    public void findByIdRoleAdmin() {
//        /**
//         * 회원가입 -
//         * Role 부여 (Admin)
//         * 해당 아이디로 로그인 ( loginService에 login 로직 합쳐서 그걸 이용해서 해보기)
//         * 다이어리 조회
//         * find
//         */
//
//        Member user = makeMockUser();
//        DiaryPrivate diary = makeMockDiary(user);
//        Member admin = makeMockAdmin();
//    }


    private Member makeMockUser() {
        Member member = Member.builder()
                            .userId("testUser")
                            .userPw("test")
                            .nickName("test")
                            .build();
        memberService.save(member);

        MemberRole memberRole = MemberRole.builder()
                .member(member)
                .role(roleService.find(RoleList.USER))
                .build();
        roleService.saveMemberRole(memberRole);

        return member;
    }

    private Member makeMockAdmin() {
        Member member = Member.builder()
                .userId("testUser")
                .userPw("test")
                .nickName("test")
                .build();
        memberService.save(member);

        MemberRole memberRole = MemberRole.builder()
                .member(member)
                .role(roleService.find(RoleList.ADMIN))
                .build();
        roleService.saveMemberRole(memberRole);

        return member;
    }

    private DiaryPrivate makeMockDiary(Member member, LocalDateTime date) {
         DiaryPrivate diary = DiaryPrivate.builder()
                                        .title("test")
                                        .contents("test")
                                        .member(member)
                                        .diaryDay(date)
                                        .build();

        return diaryPrivateService.save(diary);
    }

}