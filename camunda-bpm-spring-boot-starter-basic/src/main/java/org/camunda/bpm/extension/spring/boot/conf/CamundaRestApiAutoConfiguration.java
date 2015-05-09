package org.camunda.bpm.extension.spring.boot.conf;

import org.camunda.bpm.extension.spring.boot.rest.CamundaJerseyConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(name = "org.camunda.bpm.engine.rest.impl.CamundaRestResources")
@ConditionalOnWebApplication
@AutoConfigureBefore(JerseyAutoConfiguration.class)
@AutoConfigureAfter({
  CamundaAutoConfiguration.class,
  CamundaJpaAutoConfiguration.class,
  DataSourceCamundaAutoConfiguration.class,
  SecurityAutoConfiguration.class })
public class CamundaRestApiAutoConfiguration {

  @Bean
  public CamundaJerseyConfig jerseyConfig() {
      return new CamundaJerseyConfig();
    }

//  @Configuration
//  @ConditionalOnMissingBean(WebSecurityConfigurerAdapter.class)
//  @EnableWebSecurity
//  @EnableWebMvcSecurity
//  public static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//      return new HttpBasicAuthenticationProvider();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////      http
////        .authenticationProvider(authenticationProvider())
////        .csrf().disable()
////        .authorizeRequests()
////          .anyRequest().authenticated()
////          .and()
////        .httpBasic();
//    }
//  }

}
