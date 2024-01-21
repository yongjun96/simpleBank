package kimshop.kimcoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BankApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankApiApplication.class, args);

        /*
        // 등록된 빈을 확인해 보기
        ConfigurableApplicationContext context = SpringApplication.run(BankApiApplication.class, args);

        // getBeanDefinitionNames() : String의 배열타입으로 리턴
        String[] iocNames = context.getBeanDefinitionNames();
        for (String iocName : iocNames) {
            System.out.println(iocName);
        }
        */
    }

}
