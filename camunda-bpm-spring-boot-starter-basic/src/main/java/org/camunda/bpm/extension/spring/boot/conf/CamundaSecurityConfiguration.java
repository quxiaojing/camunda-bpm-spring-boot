package org.camunda.bpm.extension.spring.boot.conf;

import org.camunda.bpm.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@AutoConfigureBefore(SecurityAutoConfiguration.class)
public class CamundaSecurityConfiguration {

  @Configuration
  @ConditionalOnClass(UserDetailsService.class)
  public static class UserDetailsServiceConfiguration extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private IdentityService identityService;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
      // TODO return implementation of UserDetailsService
      return null;
    }

  }

}
