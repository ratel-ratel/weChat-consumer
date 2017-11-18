package cn.vpclub.shm.shcmcc.consumer;

import cn.vpclub.spring.boot.cors.autoconfigure.CorsConfiguration;
import cn.vpclub.spring.boot.cors.autoconfigure.CorsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;
import cn.vpclub.spring.boot.swagger.autoconfigure.EnableSBCSwagger;

@SpringBootApplication
@EnableConfigurationProperties({CorsProperties.class})
@EnableSBCSwagger
@Slf4j
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(
        ConsumerApplication.class);
        application.addListeners(
                new ApplicationPidFileWriter("app.pid"));
        application.run(args);
    }
    @Bean
    public CorsConfiguration corsConfiguration(){
        return new CorsConfiguration();
    }

}
