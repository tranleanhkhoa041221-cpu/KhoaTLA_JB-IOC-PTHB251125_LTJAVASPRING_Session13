package ra.edu.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Throwable cause = (Throwable) request.getAttribute("jwt_exception");

        String message = "Unauthorized: Token không hợp lệ hoặc đã hết hạn";

        if (cause instanceof ExpiredJwtException) {
            message = "Expired token";
        } else if (cause instanceof MalformedJwtException) {
            message = "Invalid token";
        } else if (cause instanceof UnsupportedJwtException) {
            message = "Unsupported token";
        } else if (cause instanceof IllegalArgumentException) {
            message = "Jwt key string invalid";
        }

        log.error("Unauthorized error: {}",
                cause != null ? cause.getMessage() : authException.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> body = new HashMap<>();
        body.put("status", 401);
        body.put("error", "UNAUTHORIZED");
        body.put("message", message);
        body.put("path", request.getServletPath());

        new ObjectMapper().writeValue(response.getOutputStream(), body);

    }
}
