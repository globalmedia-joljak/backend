package kr.joljak.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "kr.joljak.admin")
public class JoljakAdminApplication {
  public static void main(String[] args){
    SpringApplication.run(JoljakAdminApplication.class,args);
  }
}
