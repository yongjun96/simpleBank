package kimshop.kimcoding.config.auth;

import kimshop.kimcoding.domain.user.User;
import kimshop.kimcoding.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

    @Autowired private UserRepository userRepository;

    //시큐리티로 로그인이 될 때, 시큐리티가 loadUserByUsername() 실행해서 username을 체크!!
    // 체크해서 없다면 오려
    // 체크해서 있으면 정상적으로 시큐리티 컨텍스트 내부에 로그인된 세션이 만들어 짐
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new InternalAuthenticationServiceException("인증 실패") // 나중에 테스트 때 설명.
        );
        return new LoginUser(user);
    }
}
