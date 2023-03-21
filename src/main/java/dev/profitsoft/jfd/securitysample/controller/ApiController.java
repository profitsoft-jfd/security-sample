package dev.profitsoft.jfd.securitysample.controller;

import dev.profitsoft.jfd.securitysample.dto.UserInfoDto;
import dev.profitsoft.jfd.securitysample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

  private final UserService userService;

  @GetMapping("/users")
  public List<UserInfoDto> listUsers() {
    return userService.listUsers();
  }

}
