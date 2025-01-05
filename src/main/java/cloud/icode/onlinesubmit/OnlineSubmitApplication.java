package cloud.icode.onlinesubmit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cloud.icode.onlinesubmit.dao")
public class OnlineSubmitApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineSubmitApplication.class, args);
    }

}
