package com.hotel.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class TestConfigurationUserDetails {
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {

        var adminUser = new org.springframework.security.core.userdetails.User("admin@test.com", "123", Arrays.asList(
                new SimpleGrantedAuthority("ROLE_ADMIN")));

        var clientUser = new org.springframework.security.core.userdetails.User("client@test.com", "123", Arrays.asList(
                new SimpleGrantedAuthority("ROLE_CLIENT")));

        return new InMemoryUserDetailsManager(Arrays.asList(adminUser, clientUser));
    }


}
