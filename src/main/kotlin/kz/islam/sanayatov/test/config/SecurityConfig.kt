package kz.islam.sanayatov.test.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.lang.Exception


@Configuration
class CustomWebSecurityConfigurerAdapter : WebSecurityConfigurerAdapter() {

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("islam").password(passwordEncoder().encode("password")).roles("USER").and()
            .withUser("helder").password(passwordEncoder().encode("password")).roles("USER").and()
            .withUser("mikis").password(passwordEncoder().encode("password")).roles("USER").and()
            .withUser("tatsiana").password(passwordEncoder().encode("password")).roles("USER")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .anyRequest().authenticated().and().formLogin()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}