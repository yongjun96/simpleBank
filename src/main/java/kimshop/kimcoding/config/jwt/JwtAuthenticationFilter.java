package kimshop.kimcoding.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kimshop.kimcoding.Dto.user.UserReqDto;
import kimshop.kimcoding.Dto.user.UserRespDto;
import kimshop.kimcoding.Dto.user.UserRespDto.LoginRespDto;
import kimshop.kimcoding.config.auth.LoginUser;
import kimshop.kimcoding.util.CustomResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;

import static kimshop.kimcoding.Dto.user.UserReqDto.*;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final Logger log = LoggerFactory.getLogger(getClass());
    private AuthenticationManager authenticationManager;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/login");
        this.authenticationManager = authenticationManager;
    }

    //Post : /api/login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            log.debug("디버그 : attemptAuthentication 호출됨");
        try{
            ObjectMapper om = new ObjectMapper();

            LoginReqDto loginReqDto = om.readValue(request.getInputStream(), LoginReqDto.class);

            //강제 로그인
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginReqDto.getUsername(), loginReqDto.getPassword());
            // UserDetailsService의 loadUserByUSername 호출
            // JWT 쓴다 해도, 컨트롤러 진입하면 시큐리티의 권한체크, 인증체크 도움을 받을 수 있게 "세션"을 만든다.
            // 이 세션의 유효기간은 request하고, response하면 끝!
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            return authentication;

        }catch (Exception e){
            // unsuccessfulAuthentication 호출
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    //로그인 실패 (nusu로 자동 완성)
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        CustomResponseUtil.fail(response, "로그인 실패", HttpStatus.UNAUTHORIZED);
    }

    //return authentication 잘 작동하면 successfulAuthentication 메서드 호출된다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        log.debug("디버그 : successfulAuthentication 호출됨");

        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        String jwtToken = JwtProcess.create(loginUser);
        response.addHeader(JwtVO.HEADER, jwtToken);

        LoginRespDto loginRespDto = new LoginRespDto(loginUser.getUser());
        CustomResponseUtil.success(response, loginRespDto);

    }
}
