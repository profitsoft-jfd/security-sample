package dev.profitsoft.jfd.securitysample.service;

import dev.profitsoft.jfd.securitysample.data.Role;
import dev.profitsoft.jfd.securitysample.data.UserData;
import dev.profitsoft.jfd.securitysample.dto.UserInfoDto;
import dev.profitsoft.jfd.securitysample.repository.RoleRepository;
import dev.profitsoft.jfd.securitysample.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * This is a service for users.
 * <p>
 *   It implements {@link UserDetailsService} to provide the user details for Spring Security.
 *   It also provides the user details for the API.
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  @PostConstruct
  public void init() {
    PasswordEncoder passwordEncoder = PasswordEncoderFactories
        .createDelegatingPasswordEncoder();
    userRepository.save(UserData.builder()
        .username("user")
        .password("{noop}usersecret")
        .role("USER")
        .enabled(true)
        .build()
    );
    userRepository.save(UserData.builder()
        .username("admin")
        // {bcrypt}$2a$10$H7EO3AAVh9arrv7kXUJrnevAO0rxP7EoYFpW8vXAkLfznnJvb5XOe
        .password(passwordEncoder.encode("adminsecret"))
        .role("ADMIN")
        .enabled(true)
        .build()
    );
    userRepository.save(UserData.builder()
        .username("apiman")
        .password(passwordEncoder.encode("apisecret"))
        .role("API_USER")
        .enabled(true)
        .build()
    );
  }

  public List<UserInfoDto> listUsers() {
    return userRepository.findAll().stream()
        .map(this::toInfoDto)
        .toList();
  }

  private UserInfoDto toInfoDto(UserData user) {
    return UserInfoDto.builder()
        .id(user.getId())
        .username(user.getUsername())
        .role(user.getRole())
        .enabled(user.isEnabled())
        .build();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .map(this::toUserDetails)
        .orElseThrow(() ->
            new UsernameNotFoundException("User with name '%s' not found".formatted(username)));
  }

  private UserDetails toUserDetails(UserData user) {
    return User.withUsername(user.getUsername())
        .password(user.getPassword())
        .authorities(collectAuthorities(user.getRole()))
        .disabled(!user.isEnabled())
        .build();
  }

  private List<GrantedAuthority> collectAuthorities(String role) {
    return roleRepository.getRole(role)
        .map(Role::getPrivileges)
        .stream().flatMap(Collection::stream)
        .map(priv -> (GrantedAuthority)new SimpleGrantedAuthority("PRIV_" + priv))
        .toList();
  }

}
