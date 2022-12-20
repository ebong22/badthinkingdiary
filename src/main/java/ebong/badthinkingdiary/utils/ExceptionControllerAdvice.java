package ebong.badthinkingdiary.utils;

import ebong.badthinkingdiary.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
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


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseDTO illegalStateExHandle(IllegalStateException e) {

        log.error("[illegalStateExHandle]\n", e);
        return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, false, "IllegalState", null);
    }


    // @TODO : HTTP STATUS 이게 맞는지..?
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseDTO noSuchElementExHandle(NoSuchElementException e) {

        log.error("[noSuchElementExHandle]\n", e);
        return new ResponseDTO(HttpStatus.BAD_REQUEST, false, "NoSuchElement", null);
    }


    // @TODO : HTTP STATUS 이게 맞는지..? / 이 오류에 대해 뭔지도 찾아보기.. 내가 의도한대로가 맞는지
    // 내가 생각한것 : db조회한 데이터가 없을 때 나는 exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseDTO emptyResultDataAccessExHandle(EmptyResultDataAccessException e) {

        log.error("[emptyResultDataAccessExHandle]\n", e);
        return new ResponseDTO(HttpStatus.BAD_REQUEST, false, "emptyResultDataAccess", null);
    }
}
