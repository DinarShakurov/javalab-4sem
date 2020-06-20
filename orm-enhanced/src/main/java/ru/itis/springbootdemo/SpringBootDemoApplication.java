package ru.itis.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
@EnableSwagger2
public class SpringBootDemoApplication {

//    @Bean
//    public InputStream costsInputStream() {
//        try {
//            return new FileInputStream("costs.txt");
//        } catch (IOException e) {
//            throw new IllegalArgumentException(e);
//        }
//    }
//
//    @Bean
//    public InputStream goodsInputStream() {
//        try {
//            return new FileInputStream("goods.txt");
//        } catch (IOException e) {
//            throw new IllegalArgumentException(e);
//        }
//    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.itis.springbootdemo.api"))
                .paths(PathSelectors.any())
                .build();
    }


    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

}
