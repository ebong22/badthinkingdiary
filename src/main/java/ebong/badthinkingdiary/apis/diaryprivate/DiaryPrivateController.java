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

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), diaryPrivateToDiaryViewDto(diary));
    }


    /**
     * DiaryPrivate 조회(다건) : 특정 Member에 해당하는 diary 전체를 조회
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
                DiaryViewDTO dto = diaryPrivateToDiaryViewDto(diary);

                if(dto.getOpacity() > 0){
                    returnDiaryList.add(dto);
                }
            }
            return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), returnDiaryList);
        }
        throw new NoSuchElementException("diaryList empty");
    }


    /**
     * DiaryPrivate 저장
     * @param saveDto
     * @param bindingResult
     * @param id
     * @return ResponseDTO
     */
    @Operation(summary = "일기 저장", description = "일기를 저장함")
    @PostMapping("/save/{id}") // @TODO 시큐리티 : id부분 나중에 로그인된 멤버 정보 서버 안에서 가져올 수 있으면 pathVariable말고 그걸로 처리
    public ResponseDTO save(@Validated @RequestBody DiarySaveDTO saveDto, BindingResult bindingResult, @PathVariable Long id) {

        if (bindingResult.hasErrors()) {
            log.debug("bindingResult has Errors = {}", bindingResult);
            throw new IllegalArgumentException("bindingResult has Errors");
        }

        DiaryPrivate diary = diaryPrivateService.save(diarySaveDtoToDiaryPrivate(saveDto, id));
        return new ResponseDTO(HttpStatus.OK, true, "save complete", diary);
    }


    /**
     * DiaryPrivate 삭제
     * @param id
     */
    @Operation(summary = "일기 삭제", description = "일기 id를 통해 일기를 삭제함")
    @GetMapping("/delete/{id}")
    public ResponseDTO delete(@PathVariable Long id) {
        diaryPrivateService.deleteById(id);
        return new ResponseDTO(HttpStatus.OK, true, "delete complete", null);
    }


    /**
     * DiarySaveDTO to DiaryPrivate
     * @param saveDto
     * @return DiaryPrivate
     */
    private DiaryPrivate diarySaveDtoToDiaryPrivate(DiarySaveDTO saveDto, Long id) {
        return DiaryPrivate.builder()
                .member(memberService.findById(id)) // 나중에 dto에서 빼고 현재 로그인된 유저 정보 찾아와도 될 듯
                .title(saveDto.getTitle())
                .contents(saveDto.getContents())
                .diaryDay(saveDto.getDiaryDay())
                .icon(saveDto.getIcon())
                .build();
    }


    /**
     * DiaryPrivate to DiaryViewDTO
     * @param diaryPrivate
     * @return DiaryViewDTO
     */
    private DiaryViewDTO diaryPrivateToDiaryViewDto(DiaryPrivate diaryPrivate) {
        return DiaryViewDTO.builder()
                .title(diaryPrivate.getTitle())
                .contents(diaryPrivate.getContents())
                .diaryDay(diaryPrivate.getDiaryDay())
                .icon(diaryPrivate.getIcon())
                .opacity(commonUtils.getOpacity(diaryPrivate.getDiaryDay()))
                .build();
    }


    @RequestMapping("/test")
    public List<DiaryPrivate> test (){
        return diaryPrivateService.findByDiaryDayLessThan(LocalDate.now().atStartOfDay());
    }
}
