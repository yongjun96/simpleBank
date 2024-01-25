package kimshop.kimcoding.handler.aop;

import kimshop.kimcoding.Dto.ResponseDto;
import kimshop.kimcoding.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component // 메모리에 띄운다
@Aspect // pointcut, Advice
public class CustomValidationAdvice {

    /**
     * get, delete, post (body), put (body)
     * 데이터를 주는 경우(body가 있을 수 있는 경우) 유효성 검사가 있어야 함.
     *
     * post, put 어노테이션이 걸려있는 컨트롤러가 실행이 될 때 동작.
     * BindingResult라는 매개변수가 있다면  if(arg instanceof BindingResult) 동작
     * error가 '있'는 경우 : throw new CustomValidationException("유효성 검사 실패", errorMap);
     * error가 '없'는 경우 : return proceedingJoinPoint.proceed(); -> 정상적으로 해당 메서드 실행
     */

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postMapping(){}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putMapping(){}

    // post, put에 적용 시키겠다.
    @Around("postMapping() || putMapping()") // joinPoint의 전, 후 제어 가능
    // @Before() : 메서드가 실행되기 전
    // @After() : 메서드가 실행된 후
    public Object validationAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs(); // joinPoint의 매개변수

        for (Object arg: args) {
            if(arg instanceof BindingResult){
                BindingResult bindingResult = (BindingResult) arg;

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new HashMap<>();

                    // bindingResult.getFieldErrors() 의 error를 FieldError로 for문 돌리고
                    // getField()와 getDefaultMessage()를 map에 담는다.
                    for(FieldError error : bindingResult.getFieldErrors()){
                        errorMap.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성 검사 실패", errorMap);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }
}

