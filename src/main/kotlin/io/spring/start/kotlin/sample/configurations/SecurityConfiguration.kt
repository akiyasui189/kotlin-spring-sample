package io.spring.start.kotlin.sample.configurations

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class SecurityConfiguration: WebSecurityConfigurerAdapter () {

    override fun configure(http: HttpSecurity) {
        // Auth
        http.authorizeRequests()
                .antMatchers("/")
                .permitAll()
        // CSRF
        http.csrf().disable()
    }

}