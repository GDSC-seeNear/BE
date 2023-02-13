package seeNear.seeNear_BE.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
public class GlobalExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }catch (CustomException e){
            log.error("handleCustomException in filter throw CustomException : {}", e.getErrorCode());
            ObjectMapper objectMapper = new ObjectMapper();
            //직렬화 문제 해결 코드
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            var errorResponse = ErrorResponse.toResponseEntity(e.getErrorCode(),e.getValue());
            try{
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            }catch (IOException er){
                er.printStackTrace();
            }

        }
    }
}
