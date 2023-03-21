package dev.profitsoft.jfd.securitysample.repository;

import dev.profitsoft.jfd.securitysample.data.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class RoleRepository {

  private static final Map<String, Role> ROLES = List.of(
      Role.builder()
          .id("USER")
          .privileges(List.of())
          .build(),
      Role.builder()
          .id("ADMIN")
          .privileges(List.of("USER_MANAGEMENT"))
          .build(),
      Role.builder()
          .id("API_USER")
          .privileges(List.of("API_ACCESS"))
          .build()
      )
      .stream()
      .collect(Collectors.toUnmodifiableMap(
          Role::getId,
          Function.identity())
      );

  public Optional<Role> getRole(String id) {
    return Optional.ofNullable(ROLES.get(id));
  }

}
