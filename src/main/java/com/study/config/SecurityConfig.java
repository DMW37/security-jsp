package com.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author 35612
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置认证方式[内存或者数据库]
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("{noop}123").roles("USER");
    }

    /**
     * 配置
     * 1.释放静态资源
     * 2.配置拦截路径
     * 3.remember-me
     * 4.配置登入与退出
     * 5.配置csrf
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login.jsp","/failer.jsp","/css/**","/img/**","/plugins/**").permitAll()
                .antMatchers("/**").hasAnyRole("USER").anyRequest().authenticated()
                .and()
                .formLogin().loginProcessingUrl("/login").loginPage("/login.jsp").successForwardUrl("/index.jsp").failureForwardUrl("/failer.jsp").permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login.jsp").invalidateHttpSession(true).permitAll()
                .and()
                .csrf().disable();

    }
}