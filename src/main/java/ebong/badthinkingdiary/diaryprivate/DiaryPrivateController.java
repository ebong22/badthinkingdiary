package ebong.badthinkingdiary.diaryprivate;

import ebong.badthinkingdiary.domain.DiaryPrivate;
import ebong.badthinkingdiary.dto.DiarySaveDTO;
import ebong.badthinkingdiary.dto.ResponseDTO;
import ebong.badthinkingdiary.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Tag(name = "다이어리_개인", description = "나쁜마음 일기 _ 개인 일기장")
@RestController
@AllArgsConstructor
@RequestMapping("diary/private")
public class DiaryPrivateController {

    private final DiaryPrivateService diaryPrivateService;
    private final MemberService memberService;


    @Operation(summary = "일기 조회(Id)", description = "일기 id를 통해 단일 일기를 조회함")
    @GetMapping("/{id}")
    public ResponseDTO findById(@PathVariable Long id) {
        DiaryPrivate diary = diaryPrivateService.findById(id);
        return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), diary);
    }


    @Operation(summary = "일기 조회(memberId)", description = "멤버 id를 통해 단일 일기를 조회함")
    @GetMapping("/member/{memberId}")
    public ResponseDTO findByMemberId(@PathVariable Long memberId) {
        List<DiaryPrivate> diarys = diaryPrivateService.findByMemberId(memberId);

        if (diarys.size() > 0) {
            return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), diarys);
        }
        throw new NoSuchElementException("empty diary");
    }


    @Operation(summary = "일기 저장", description = "일기 저장")
    @PostMapping("/save")
    public ResponseDTO save(@Validated @RequestBody DiarySaveDTO saveDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.debug("bindingResult has Errors = {}", bindingResult);
            throw new IllegalArgumentException("bindingResult has Errors");
        }

        DiaryPrivate diary = diaryPrivateService.save(diarySaveDtoToDiaryPrivate(saveDTO));
        return new ResponseDTO(HttpStatus.OK, true, "save complete", diary);
    }


    private DiaryPrivate diarySaveDtoToDiaryPrivate(DiarySaveDTO saveDTO) {
        return DiaryPrivate.builder()
                .member(memberService.findById(saveDTO.getMemberId())) // 나중에 dto에서 빼고 현재 로그인된 유저 정보 찾아와도 될 듯
                .title(saveDTO.getTitle())
                .contents(saveDTO.getContents())
                .diaryDay(saveDTO.getDiaryDay())
                .icon(saveDTO.getIcon())
                .build();
    }


    @Operation(summary = "일기 삭제", description = "일기 id를 통해 단일 일기를 삭제함")
    @GetMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        diaryPrivateService.deleteById(id);
    }
}
