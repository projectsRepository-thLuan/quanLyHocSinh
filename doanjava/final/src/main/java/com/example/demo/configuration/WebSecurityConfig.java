package com.example.demo.configuration;

import com.example.demo.service.GiaoVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/login", "/logout").permitAll();

        // Trang /userInfo yêu cầu phải login với vai trò ROLE_USER hoặc ROLE_ADMIN.
        // Nếu chưa login, nó sẽ redirect tới trang /login.
        http.authorizeRequests().antMatchers("/giaovieninfo").access("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')");
        // Trang chỉ dành cho ADMIN
        http.authorizeRequests().antMatchers("/quantri_admin").access("hasRole('ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/diem/**").access("hasRole('ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/hocsinh/**").access("hasRole('ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/giaovien/**").access("hasRole('ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/lop/**").access("hasRole('ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/monhoc/**").access("hasRole('ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/loaidiem/**").access("hasRole('ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/chucvu/**").access("hasRole('ROLE_ADMIN')");

        // Khi người dùng đã login, với vai trò XX.
        // Nhưng truy cập vào trang yêu cầu vai trò YY,
        // Ngoại lệ AccessDeniedException sẽ ném ra.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
        http.authorizeRequests().and().formLogin()//
                // Submit URL của trang login
                //.loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/login")//
                .defaultSuccessUrl("/giaovieninfo")//
                .failureUrl("/login?error")//
                .usernameParameter("username")//
                .passwordParameter("password")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/index");
    }
}
