package kimshop.kimcoding.web;

import jakarta.validation.Valid;
import kimshop.kimcoding.Dto.ResponseDto;
import kimshop.kimcoding.Dto.user.UserReqDto;
import kimshop.kimcoding.Dto.user.UserReqDto.JoinReqDto;
import kimshop.kimcoding.Dto.user.UserRespDto;
import kimshop.kimcoding.Dto.user.UserRespDto.JoinRespDto;
import kimshop.kimcoding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    // @RequestBody : json으로 받기 위해서 작성
    // @Valid : 해당 객체의 유효성 검사를 하겠다고 명시
    // BindingResult : @Valid 유효성 검사가 통과하지 못하면 여기에 다 담김.
    public ResponseEntity<?> join(@RequestBody @Valid JoinReqDto joinReqDto, BindingResult bindingResult){

        JoinRespDto joinRespDto = userService.join(joinReqDto);

        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 완료", joinRespDto), HttpStatus.CREATED);
    }
}
