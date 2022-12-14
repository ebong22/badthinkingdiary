package ebong.badthinkingdiary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ResponseDTO {

    private HttpStatus  code;
    private Boolean     success;
    private String      message;
    private Object      data;

}
