package ebong.badthinkingdiary.apis.diarypublic;

import ebong.badthinkingdiary.domain.DiaryPublic;
import ebong.badthinkingdiary.dto.DiarySaveDTO;
import ebong.badthinkingdiary.dto.DiaryViewDTO;
import ebong.badthinkingdiary.dto.ResponseDTO;
import ebong.badthinkingdiary.utils.CommonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Tag(name = "다이어리_익명 공개 일기", description = "나쁜마음 일기_익명 공개 일기장")
@RestController
@RequiredArgsConstructor
@RequestMapping("diary/public")
public class DiaryPublicController {

    private final DiaryPublicService diaryPublicService;
    private final CommonUtils commonUtils;


    /**
     * DiaryPublic 조회(단건)
     * @param id
     * @return ResponseDTO
     */
    @Operation(summary = "일기 조회(Id)", description = "일기 id를 통해 단일 일기를 조회함")
    @GetMapping("/{id}")
    public ResponseDTO findById(@PathVariable Long id) {
        DiaryPublic diary = diaryPublicService.findById(id);
        return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), diaryPublicToDiaryViewDto(diary));
    }

    /**
     * DiaryPublic 조회(전체)
     * @return ResponseDTO
     */
    @Operation(summary = "일기 조회", description = "익명일기 전체를 조회함")
    @GetMapping("/all")
    public ResponseDTO findAll() {
        List<DiaryPublic> diaryList = diaryPublicService.findAll();
        if (diaryList.size() > 0) {
            List<DiaryViewDTO> returnDiaryList = new ArrayList<>();

            for (DiaryPublic diary : diaryList) { // diary to dto
                DiaryViewDTO dto = diaryPublicToDiaryViewDto(diary);

                if (dto.getOpacity() > 0) {
                    returnDiaryList.add(dto);
                }
            }
            return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), returnDiaryList);
        }
        throw new NoSuchElementException("diaryList empty");
    }

    /**
     * DiaryPublic 저장
     * @param saveDto
     * @param bindingResult
     * @return ResponseDTO
     */
    @Operation(summary = "일기 저장", description = "일기를 저장함")
    @PostMapping("/save")
    public ResponseDTO save(@Validated @RequestBody DiarySaveDTO saveDto, BindingResult bindingResult) {
        commonUtils.returnError(bindingResult);

        DiaryPublic diary = diaryPublicService.save(diarySaveDtoToDiaryPublic(saveDto));
        return new ResponseDTO(HttpStatus.OK, true, "save complete", diary);
    }

    /**
     * DiaryPublic 삭제
     * @param id
     */
    @Operation(summary = "일기 삭제", description = "일기 id를 통해 일기를 삭제함")
    @GetMapping("/delete/{id}")
    public ResponseDTO delete(@PathVariable Long id) {
        diaryPublicService.delete(id);
        return new ResponseDTO(HttpStatus.OK, true, "delete complete", null);
    }

    /**
     * DiarySaveDTO to diaryPublic
     * @param saveDto
     * @return DiaryPublic
     */
    private DiaryPublic diarySaveDtoToDiaryPublic(DiarySaveDTO saveDto) {
        return DiaryPublic.builder()
                .title(saveDto.getTitle())
                .contents(saveDto.getContents())
                .diaryDay(saveDto.getDiaryDay())
                .icon(saveDto.getIcon())
                .build();
    }

    /**
     * DiaryPublic to DiaryViewDTO
     * @param diaryPublic
     * @return DiaryViewDTO
     */
    private DiaryViewDTO diaryPublicToDiaryViewDto(DiaryPublic diaryPublic) {
        return DiaryViewDTO.builder()
                .title(diaryPublic.getTitle())
                .contents(diaryPublic.getContents())
                .diaryDay(diaryPublic.getDiaryDay())
                .icon(diaryPublic.getIcon())
                .opacity(commonUtils.getOpacity(diaryPublic.getDiaryDay()))
                .build();
    }

}
