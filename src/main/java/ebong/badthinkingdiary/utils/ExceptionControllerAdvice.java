package ebong.badthinkingdiary.utils;

import ebong.badthinkingdiary.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
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

    // @TODO 리팩토링 :  request 잘못되면 나는 오류같은데 BODY 없는거 말고도 문제가 있을 것 같으니 message는 수정 필요
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseDTO httpMessageNotReadableExHandle(HttpMessageNotReadableException e) {
        log.error("[httpMessageNotReadableExHandle]\n", e);
        return new ResponseDTO(HttpStatus.BAD_REQUEST, false, "bad request", null);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseDTO badCredentialsExHandle(BadCredentialsException e) {
        log.error("[badCredentialsExHandle]\n", e);
        return new ResponseDTO(HttpStatus.UNAUTHORIZED, false, "Credentials failed", null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseDTO duplicateKeyExHandle(DuplicateKeyException e) {
        log.error("[duplicateKeyExHandle]\n", e);
        return new ResponseDTO(HttpStatus.BAD_REQUEST, false, "already exist", null);
    }
}
