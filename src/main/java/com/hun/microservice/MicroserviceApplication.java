package com.hun.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication //스프링 부트 서비스의 진입점을 지정
@RestController //이 클래스의 코드를 스프링 RestController 클래스로 노출하도록 지정
@RequestMapping(value = "hello")
public class MicroserviceApplication {

    public static void main(String[] args) {

        SpringApplication.run(MicroserviceApplication.class, args);
    }

    //두 매개변수 first와 last를 입력받는 GET 기반의 REST 엔드포인트 노출
    @RequestMapping(value = "/{firstName}/{lastName}", method = RequestMethod.GET)
    //@PathVariable을 통해 url에 전달된 first, lastName 매개변수를 hello 함수에 전달에 두 변수에 매핑해준다.
    public String hello(@PathVariable("firstName") String firstName ,@PathVariable("lastName") String lastName){
        return String.format("{\"message\":\"Hello %s %s \"}", firstName, lastName);
    }
}
