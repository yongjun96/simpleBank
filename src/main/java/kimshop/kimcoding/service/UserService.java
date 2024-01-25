package kimshop.kimcoding.service;

import kimshop.kimcoding.Dto.user.UserReqDto;
import kimshop.kimcoding.Dto.user.UserRespDto;
import kimshop.kimcoding.domain.user.User;
import kimshop.kimcoding.domain.user.UserEnum;
import kimshop.kimcoding.domain.user.UserRepository;
import kimshop.kimcoding.ex.CustomApiException;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 회원가입
     * Dto를 요청받고, Dto로 응답한다.
     */
    @Transactional
     public UserRespDto.JoinRespDto join(UserReqDto.JoinReqDto userReqDto){

        // 1. 동일 유저네임 존재 검사
        // 2. 패스워드 인코딩
        // 3. dto 응답

        Optional<User> findUser = userRepository.findByUsername(userReqDto.getUsername());

        //findUser가 존재 한다면.
        if(findUser.isPresent()){
            // 존재한다면 중복되었다는 뜻.
            throw new CustomApiException("동일한 유저이름이 존재합니다.");
        }

        User userPs = userRepository.save(userReqDto.toEntity(passwordEncoder));

        return new UserRespDto.JoinRespDto(userPs);

     }
}
