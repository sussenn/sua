package com.itc.sua.system.config;

import com.itc.sua.system.filter.TokenFilter;
import com.itc.sua.system.handler.AccessRejectedHandler;
import com.itc.sua.system.handler.AuthEntryPointHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @ClassName SecurityConfig
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/20
 */
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法上的权限注解使用
public class SecurityConfig {

    private final TokenFilter tokenFilter;
    private final AuthEntryPointHandler authEntryPointHandler;
    private final AccessRejectedHandler accessRejectedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 密码加密方式
        return new BCryptPasswordEncoder();
    }

    /**
     * Security认证器
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((auth) ->
                        auth.mvcMatchers("/auth/login",
                                        "/auth/getImgCode",
                                        "/auth/getSmsCode").permitAll()
                                .antMatchers(
                                        HttpMethod.GET,
                                        "/*.html",
                                        "/**/*.html",
                                        "/**/*.css",
                                        "/**/*.js").permitAll()
                                // 放行OPTIONS请求
                                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                // 其他所有请求需要认证
                                .anyRequest().authenticated())
                .csrf().disable()
                // 跨域处理
                //.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
                // 授权异常的处理
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPointHandler)
                .accessDeniedHandler(accessRejectedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 防止iframe 造成跨域
                .headers().frameOptions().disable()
                .and()
                // tokenFilter配置在UPAuthFilter之前
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    //跨域
    private CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
