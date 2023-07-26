package augusto108.ces.appointmenttracker.security;

import augusto108.ces.appointmenttracker.services.EmployeeRoleService;
import augusto108.ces.appointmenttracker.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:users.properties")
@Profile("!test")
public class ApplicationSecurityConfig {
    private final EmployeeService employeeService;
    private final EmployeeRoleService employeeRoleService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(EmployeeService employeeService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(employeeService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests(
                registry -> registry
                        .antMatchers(HttpMethod.GET).hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN", "TRAINEE")
                        .antMatchers(HttpMethod.POST).hasAnyRole("MANAGER", "ADMIN")
                        .antMatchers(HttpMethod.PATCH).hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")
        );

        httpSecurity.httpBasic();
        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();

        return httpSecurity.build();
    }
}
