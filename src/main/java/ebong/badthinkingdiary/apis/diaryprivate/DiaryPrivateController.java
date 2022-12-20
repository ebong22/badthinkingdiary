package ebong.badthinkingdiary.apis.diaryprivate;

import ebong.badthinkingdiary.domain.DiaryPrivate;
import ebong.badthinkingdiary.dto.DiarySaveDTO;
import ebong.badthinkingdiary.dto.DiaryViewDTO;
import ebong.badthinkingdiary.dto.ResponseDTO;
import ebong.badthinkingdiary.apis.member.MemberService;
import ebong.badthinkingdiary.utils.CommonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Tag(name = "다이어리_개인", description = "나쁜마음 일기_개인 일기장")
@RestController
@AllArgsConstructor
@RequestMapping("diary/private")
public class DiaryPrivateController {

    private final DiaryPrivateService diaryPrivateService;
    private final MemberService memberService;
    private final CommonUtils commonUtils;


    /**
     * DiaryPrivate 조회(단건)
     * @param id
     * @return ResponseDTO
     */
    @Operation(summary = "일기 조회(Id)", description = "일기 id를 통해 단일 일기를 조회함")
    @GetMapping("/{id}")
    public ResponseDTO findById(@PathVariable Long id) {
        DiaryPrivate diary = diaryPrivateService.findById(id);
        return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), diaryPrivateToDiaryViewDTO(diary));
    }


    /**
     * @param memberId
     * @return ResponseDTO
     */
    @Operation(summary = "일기 조회(memberId)", description = "멤버 id를 통해 멤버에 해당하는 일기 전체를 조회함")
    @GetMapping("/member/{memberId}")
    public ResponseDTO findByMemberId(@PathVariable Long memberId) {
        List<DiaryPrivate> diaryList = diaryPrivateService.findByMemberId(memberId);

        if (diaryList.size() > 0) {
            List<DiaryViewDTO> returnDiaryList = new ArrayList<>();

            for (DiaryPrivate diary : diaryList) { // diary to dto
                DiaryViewDTO dto = diaryPrivateToDiaryViewDTO(diary);

                if(dto.getOpacity() > 0){
                    returnDiaryList.add(dto);
                }
            }
            return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), returnDiaryList);
        }
        throw new NoSuchElementException("empty diary");
    }


    /**
     * DiaryPrivate 저장
     * @param saveDTO
     * @param bindingResult
     * @return ResponseDTO
     */
    @Operation(summary = "일기 저장", description = "일기를 저장함")
    @PostMapping("/save")
    public ResponseDTO save(@Validated @RequestBody DiarySaveDTO saveDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.debug("bindingResult has Errors = {}", bindingResult);
            throw new IllegalArgumentException("bindingResult has Errors");
        }

        DiaryPrivate diary = diaryPrivateService.save(diarySaveDtoToDiaryPrivate(saveDTO));
        return new ResponseDTO(HttpStatus.OK, true, "save complete", diary);
    }


    /**
     * DiaryPrivate 삭제
     * @param id
     */
    @Operation(summary = "일기 삭제", description = "일기 id를 통해 단일 일기를 삭제함")
    @GetMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        diaryPrivateService.deleteById(id);
    }


    /**
     * diarySaveDto to DiaryPrivate
     * @param saveDTO
     * @return DiaryPrivate
     */
    private DiaryPrivate diarySaveDtoToDiaryPrivate(DiarySaveDTO saveDTO) {
        return DiaryPrivate.builder()
                .member(memberService.findById(saveDTO.getMemberId())) // 나중에 dto에서 빼고 현재 로그인된 유저 정보 찾아와도 될 듯
                .title(saveDTO.getTitle())
                .contents(saveDTO.getContents())
                .diaryDay(saveDTO.getDiaryDay())
                .icon(saveDTO.getIcon())
                .build();
    }


    /**
     * DiaryPrivate to DiaryViewDTO
     * @param diaryPrivate
     * @return DiaryViewDTO
     */
    private DiaryViewDTO diaryPrivateToDiaryViewDTO(DiaryPrivate diaryPrivate) {
        return DiaryViewDTO.builder()
                .title(diaryPrivate.getTitle())
                .contents(diaryPrivate.getContents())
                .diaryDay(diaryPrivate.getDiaryDay())
                .icon(diaryPrivate.getIcon())
                .opacity(commonUtils.getOpacity(diaryPrivate.getDiaryDay()))
                .build();
    }
}
