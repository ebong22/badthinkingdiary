package ebong.badthinkingdiary.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import ebong.badthinkingdiary.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();

        if (authenticate != null) {
            ResponseDTO responseDto = new ResponseDTO(HttpStatus.FORBIDDEN, true, "[AccessDeniedHandler] Access denied", null);
            response.getWriter().write(objectMapper.writeValueAsString(responseDto));
        }
        else {
            ResponseDTO responseDto = new ResponseDTO(HttpStatus.FORBIDDEN, true, "[AccessDeniedHandler] login Access denied", null);
            response.getWriter().write(objectMapper.writeValueAsString(responseDto));
        }

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json; charset=UTF=8");
    }
}