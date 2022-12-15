package ebong.badthinkingdiary.member;

import ebong.badthinkingdiary.dto.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;

    /**
     * @// TODO: 2022/12/11
     * return값 Member 아니고 responseEntity나 responseDto 객체 생성해서 반환해주기
     * ex)
     * status httpStatus코드 / data 성공 시 반환될 데이터(Object) / errorMessage 예외 발생 시 err메세지(String)
     *
     * {
     *     "success": true,
     *     "code": 0,
     *     "message": "Ok",
     *     "data": [
     *         1,
     *         2,
     *         3
     *     ]
     * }
     */

    @GetMapping("/findById/{id}")
    public ResponseDTO findById(@PathVariable Long id) {

        try {
            return new ResponseDTO(HttpStatus.OK, true, HttpStatus.OK.toString(), memberService.findById(id));
        }catch (NoSuchElementException e) {
            throw e;
        }
    }

}
