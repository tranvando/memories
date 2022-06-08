package com.dotv.memories.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityUserConfig extends WebSecurityConfigurerAdapter {
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        // Tạo ra user trong bộ nhớ
//        // lưu ý, chỉ sử dụng cách này để minh họa
//        // Còn thực tế chúng ta sẽ kiểm tra user trong csdl
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(
//                User.withDefaultPasswordEncoder() // Sử dụng mã hóa password đơn giản
//                        .username("do")
//                        .password("do")
//                        .roles("USER") // phân quyền là người dùng.
//                        .build()
//        );
//        return manager;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.antMatcher("per/**")
                .authorizeRequests()
//                .antMatchers("/manage/**","/uploads/**","/user/**","/utils/**","/resources/**").permitAll()
                .antMatchers("/dn/**").hasAnyAuthority("D")
                //.anyRequest().authenticated()
                .and()
                // cấu hình trang đăng nhập

                .formLogin().loginPage("/login")//trang đăng nhập tùy chỉnh
//                .loginProcessingUrl("/perform_login")//url submit username, pass
//                .defaultSuccessUrl("/login_success", true)//Trang đích sau khi đăng nhập thành công
//                .failureUrl("/login.html?login_error=true")//Trang đích sau khi đăng nhập thất bại
                .permitAll()

                .and()

                //cấu hình cho phần logout
                .logout()
                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login.html")
//                .logoutSuccessUrl("/check_login")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll();



        // Khi người dùng đã login, với vai trò USER, Nhưng truy cập vào trang yêu cầu vai trò ADMIN, sẽ chuyển hướng tới trang /403
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/login");
        http.csrf().disable();
    }
}
