package ebong.badthinkingdiary.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import ebong.badthinkingdiary.dto.ResponseDTO;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        }
        catch (JwtException e) {
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseDTO responseDto = new ResponseDTO(HttpStatus.FORBIDDEN, true, "Access denied", null);

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json; charset=UTF=8");
            response.getWriter().write(objectMapper.writeValueAsString(responseDto));
        }
    }
}
