package dev.profitsoft.jfd.securitysample.data;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Role {

  private String id;

  private List<String> privileges;

}
