package ebong.badthinkingdiary.utils;

import ebong.badthinkingdiary.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;


@Slf4j
@RestControllerAdvice("ebong.badthinkingdiary") //대상: ebong.badthinkingdiary 하위 컨트롤러 전체
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseDTO illegalArgumentExHandle(IllegalArgumentException e) {

        log.error("[illegalArgumentExHandle]\n", e);
        return new ResponseDTO(HttpStatus.BAD_REQUEST, false, "IllegalArgument", null);
    }

    // @TODO : HTTP STATUS 이게 맞는지..?
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseDTO noSuchElementExHandle(NoSuchElementException e) {

        log.error("[noSuchElementExHandle]\n", e);
        return new ResponseDTO(HttpStatus.BAD_REQUEST, false, "NoSuchElement", null);
    }
}
