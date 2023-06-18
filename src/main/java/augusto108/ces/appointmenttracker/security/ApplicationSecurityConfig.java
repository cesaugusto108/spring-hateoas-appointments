package augusto108.ces.appointmenttracker.security;

import augusto108.ces.appointmenttracker.security.enums.Role;
import augusto108.ces.appointmenttracker.services.EmployeeRoleService;
import augusto108.ces.appointmenttracker.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:users.properties")
@Profile("!test")
public class ApplicationSecurityConfig {
    private final EmployeeService employeeService;
    private final EmployeeRoleService employeeRoleService;

    @Value("${users.pword}")
    private String empPassword;

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

    @Bean
    void loadDatabaseEmployeesInfo() {
        final EmployeeRole role1 = new EmployeeRole(Role.ROLE_EMPLOYEE);
        final EmployeeRole role2 = new EmployeeRole(Role.ROLE_MANAGER);
        final EmployeeRole role3 = new EmployeeRole(Role.ROLE_ADMIN);
        final EmployeeRole role4 = new EmployeeRole(Role.ROLE_TRAINEE);

        employeeRoleService.saveEmployeeRole(role1);
        employeeRoleService.saveEmployeeRole(role2);
        employeeRoleService.saveEmployeeRole(role3);
        employeeRoleService.saveEmployeeRole(role4);

        final Employee e1 = new Employee("monteiro", empPassword, true, new HashSet<>());
        final Employee e2 = new Employee("almeida", empPassword, true, new HashSet<>());
        final Employee e3 = new Employee("santos", empPassword, true, new HashSet<>());
        final Employee e4 = new Employee("araujo", empPassword, true, new HashSet<>());

        e1.getRoles().add(role1);
        e2.getRoles().add(role2);
        e3.getRoles().add(role3);
        e4.getRoles().add(role4);

        employeeService.saveEmployee(e1);
        employeeService.saveEmployee(e2);
        employeeService.saveEmployee(e3);
        employeeService.saveEmployee(e4);
    }
}
