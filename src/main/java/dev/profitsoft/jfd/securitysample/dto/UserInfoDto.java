package dev.profitsoft.jfd.securitysample.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoDto {

  private long id;

  private String username;

  private String role;

  private boolean enabled;

}
