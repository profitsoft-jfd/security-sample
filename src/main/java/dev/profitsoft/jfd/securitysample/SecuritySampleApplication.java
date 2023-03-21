package dev.profitsoft.jfd.securitysample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class SecuritySampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecuritySampleApplication.class, args);
  }

}
