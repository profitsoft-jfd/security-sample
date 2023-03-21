package dev.profitsoft.jfd.securitysample.controller;

import dev.profitsoft.jfd.securitysample.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PageController {

  private final UserService userService;

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/users")
  @PreAuthorize("hasAuthority('PRIV_USER_MANAGEMENT')")
  public String users(Model model) {
    // цей код демонструє можливості доступу до даних користувача і його прав
    SecurityContext securityContext = SecurityContextHolder.getContext();
    Authentication authentication = securityContext.getAuthentication();
    Object principal = authentication.getPrincipal();
    if (principal instanceof User user) {
      log.info("List of users displayed for user '{}'", user.getUsername());
    }
    if (authentication.getAuthorities().stream().anyMatch(
        auth -> auth.getAuthority().equals("PRIV_USER_MANAGEMENT"))) {
      log.info("Privilege PRIV_USER_MANAGEMENT exists");
    }

    model.addAttribute("users", userService.listUsers());
    return "users";
  }

}
