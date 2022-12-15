package ebong.badthinkingdiary.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@ToString
public class ResponseDTO {

    private HttpStatus  code;
    private Boolean     success;
    private String      message;
    private Object      data;

}
