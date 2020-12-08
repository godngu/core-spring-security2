package io.security.corespringsecurity2.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.security.corespringsecurity2.domain.AccountDto;
import io.security.corespringsecurity2.security.token.AjaxAuthenticationToken;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.util.StringUtils;

@Slf4j
public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

//    public AjaxLoginProcessingFilter() {
//        super(new AntPathRequestMatcher("/api/login"));
//    }
    public AjaxLoginProcessingFilter() {
        super(new AntPathRequestMatcher("/ajaxLogin", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        log.info("X-Requested-With = {}", getAjaxHeader(request));
        if (!isAjax(request)) {
            throw new IllegalStateException("Authentication is not supported");
        }

        // ajax 요청시 username, password는 JSON으로 전달한다.
        AccountDto accountDto = objectMapper.readValue(request.getReader(), AccountDto.class);
        if (StringUtils.isEmpty(accountDto.getUsername()) || StringUtils.isEmpty(accountDto.getPassword())) {
            throw new IllegalArgumentException("Username or Password is empty");
        }

        AjaxAuthenticationToken token = new AjaxAuthenticationToken(accountDto.getUsername(),
            accountDto.getPassword());

        return this.getAuthenticationManager().authenticate(token);
    }

    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(getAjaxHeader(request));
    }

    private String getAjaxHeader(HttpServletRequest request) {
        return request.getHeader("X-Requested-With");
    }
}
