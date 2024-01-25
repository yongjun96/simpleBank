package kimshop.kimcoding.temp;


import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class RegexTest {

    // java.util.regex.Pattern

    @Test
    public void onlyKr(){
        String value = "한글만가능";
        boolean result = Pattern.matches("^[가-힣]+$", value);
        System.out.println("테스트 : "+result);
    }

    @Test
    public void notKr(){
        String value = "dwsjfcndsjk123";
        boolean result = Pattern.matches("^[^ㄱ-ㅎ가-힣]*$", value);
        System.out.println("테스트 : "+result);
    }

    @Test
    public void onlyEng(){
        String value = "onlyEng";
        boolean result = Pattern.matches("^[a-zA-Z]+$", value);
        System.out.println("테스트 : "+result);
    }

    @Test
    public void notEng(){
        String value = "김용준 ";
        boolean result = Pattern.matches("^[^a-zA-Z]*$", value);
        System.out.println("테스트 : "+result);
    }

    @Test
    public void EngAndNum(){
        String value = "yongjun96";
        boolean result = Pattern.matches("^[a-zA-Z0-9]+$", value);
        System.out.println("테스트 : "+result);
    }

    @Test
    public void EngAndSize2_20(){
        String value = "yongjun";
        boolean result = Pattern.matches("^[a-zA-Z]{2,20}$", value);
        System.out.println("테스트 : "+result);
    }

    @Test
    public void usernameTest(){
        String username = "yongJun";
        boolean result = Pattern.matches("^[a-zA-Z0-9]{2,20}$", username);
        System.out.println("테스트 : "+result);
    }

    @Test
    public void fullnameTest(){
        String username = "김YongJun";
        boolean result = Pattern.matches("^[a-zA-Z가-힣]{1,20}$", username);
        System.out.println("테스트 : "+result);
    }

    @Test
    public void emailTest(){
        String username = "yongjun@gmail.com";
        boolean result = Pattern.matches("^[a-zA-Z0-9]{1,20}@[a-zA-Z0-9]{1,20}\\.[a-zA-Z]{1,10}$", username);
        System.out.println("테스트 : "+result);
    }
}
