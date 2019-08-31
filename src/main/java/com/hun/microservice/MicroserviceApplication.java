package com.hun.microservice;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication //스프링 부트 서비스의 진입점을 지정
@RestController //이 클래스의 코드를 스프링 RestController 클래스로 노출하도록 지정
@RequestMapping(value = "hello")
@EnableCircuitBreaker //서비스가 히스트릭스와 리본 라이브러리를 사용한다
@EnableEurekaClient //유레카 서비스 디스커버리 에이전트에 서비스 자신을 등록하고
                    // 서비스 디스커버리를 사용해 원격 서비스의 위치를 '검색'하도록 지정
public class MicroserviceApplication {

    RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {

        SpringApplication.run(MicroserviceApplication.class, args);
    }


    //helloRemoteServiceCall 메소드에 대한 호출을 히스트릭스 회로 차단기로 감싼다.
    @HystrixCommand(threadPoolKey="helloThreadPool")
    public String helloReomoteServiceCall(String firstName, String lastName){
        ResponseEntity<String> restExchange = restTemplate.exchange("http://logical-service-id/name/[ca]{firstName}/{lastName}",
                HttpMethod.GET,
                null,
                String.class,
                firstName, lastName);

        return restExchange.getBody();
    }

    @RequestMapping(value = "/{firstName}/{lastName}", method = RequestMethod.GET)
    public String hello(@PathVariable("firstName") String firstName , @PathVariable("lastName") String lastName){
        return helloReomoteServiceCall(firstName,lastName);
    }
}
