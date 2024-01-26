package kimshop.kimcoding.config;

import jakarta.servlet.Filter;
import kimshop.kimcoding.config.jwt.JwtAuthenticationFilter;
import kimshop.kimcoding.config.jwt.JwtVO;
import kimshop.kimcoding.domain.user.UserEnum;
import kimshop.kimcoding.util.CustomResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration // 빈에 config 등록
//@Log4j
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    // Ioc 컨테이너에 BCryptPasswordEncoder() 객체가 등록 된다.
    // @Configuration이 붙어 있는 @Bean만 작동한다.
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        log.debug("디버그 : BCryptPasswordEncoder 빈 등록");
        return new BCryptPasswordEncoder();
    }



    // JWT 서버 생성 / Session 사용 안함.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        log.debug("디버그 : filterChain 빈 등록됨.");

        //JWT 필터 등록이 필요함.
        AuthenticationManager authentication = http.getSharedObject(AuthenticationManager.class);

        http
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // iframe 허용 안함.

                .csrf(AbstractHttpConfigurer::disable) // enable이면 post맨 작동안함.

                // 자바스크립트의 요청을 허용해 줌. (Cross-Origin Resource Sharing)
                //프로토콜이며 W3C스펙이다.
                //서로 다른 Orgin 간의 데이터 및 통신을 브라우저에서 중지하기 위해 제공하는 기본 보안
                .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {

                    log.debug("디버그 : CorsConfigurationSource cors 설정 SecurityFilterChain에 등록됨.");

                    CorsConfiguration configuration = new CorsConfiguration();

                    // 모든 헤더를 다 받는다.
                    configuration.addAllowedHeader("*");

                    // 모든 메소드를 다 받는다.
                    // GET, POST, PUT, DELETE (Javascript 요청 허용)
                    configuration.addAllowedMethod("*");

                    //모든 IP 주소 허용 (프론트엔드 IP만 허용 해주는 식으로 해줘야 함.)
                    configuration.addAllowedOriginPattern("*");

                    // 클라이언트에서 쿠키 요청을 허용
                    configuration.setAllowCredentials(true);

                    //소스의 레지스터로 등록해줌
                    // "/**" -> 모든 주소 요청에 configuration설정을 넣어 준다.
                    //UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    //source.registerCorsConfiguration("/**", configuration);
                    return configuration;
                }))

                //jSessionId를 서버쪽에서 관리안한다.
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // react, 앱에서 요청을 받을 것이기 때문에 fromLogin을 사용하지 않는다.
                .formLogin(FormLoginConfigurer::disable)
                // 기본세팅
                //.formLogin(Customizer.withDefaults())

                // httpBasic은 브라우저가 팝업창을 이용해서 사용자 인증을 진행한다. (허용 안함.)
                .httpBasic(HttpBasicConfigurer::disable)
                // 기본세팅
                //.httpBasic(Customizer.withDefaults())


                //여러가지 URI 사용 가능, 다중 사용 가능
                // "/api/s/**" -> 주소에 s가 들어오면 인증이 필요하다.
                // "api/admin/**" -> role이 필요함.
                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/api/s/**").authenticated()
                                .requestMatchers("/api/admin/**").hasRole(""+UserEnum.ADMIN)  // 최근 공식문서는 ROLE_ 안붙여도 돼서 "" 비워 둠
                                .anyRequest().permitAll()) // 나머지 요청은 다 허용한다.

                //Jwt필터 적용
                .addFilter(new JwtAuthenticationFilter(authentication))

                // Exception 가로채기
                .exceptionHandling(authenticationManager ->
                        authenticationManager.authenticationEntryPoint((request, response, authException) ->{
                            // 실행된 uri가 문자열로 담김 ex). "api/s/hi"
                            //String uri = request.getRequestURI();
                                CustomResponseUtil.unAuthentication(response, "로그인을 진행해 주세요.");
                        }));


                //.build()대신 .getOrBuild() -> SpringBoot 3.x버전
                return http.build();
    }
}
