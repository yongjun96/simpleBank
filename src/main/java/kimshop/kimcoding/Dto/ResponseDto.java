package kimshop.kimcoding.Dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResponseDto<T> {

    private final Integer code; // 1성공, -1 실패
    private final String msg;
    private final T data;
}
