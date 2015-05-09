package org.camunda.bpm.extension.spring.boot.security;

import org.camunda.bpm.engine.IdentityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class IdentityServiceUserDetailsService implements UserDetailsService {

  private final IdentityService identityService;

  public IdentityServiceUserDetailsService(IdentityService identityService) {
    this.identityService = identityService;
  }

  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // TODO: return user from identity service provider
    return null;
  }

}
