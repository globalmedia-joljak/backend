package kr.joljak.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "kr.joljak.api")
public class JoljakApplication {
  public static void main(String[] args){
    SpringApplication.run(JoljakApplication.class,args);
  }
}
