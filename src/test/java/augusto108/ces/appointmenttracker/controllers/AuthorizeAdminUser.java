package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.security.entities.Employee;
import augusto108.ces.appointmenttracker.security.entities.EmployeeRole;
import augusto108.ces.appointmenttracker.security.enums.Role;
import augusto108.ces.appointmenttracker.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.List;

@PropertySource(value = "classpath:users.properties")
public abstract class AuthorizeAdminUser {

    @Value("${users.pword}")
    private String empPassword;

    @Autowired
    protected AuthorizeAdminUser(EmployeeService employeeService) {
    }

    protected RequestPostProcessor makeAuthorizedAdminUser() {
        final EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setRole(Role.ROLE_ADMIN);
        employeeRole.setId(1L);

        final Employee employee = new Employee();
        employee.setUsername("santos");
        employee.setPassword(empPassword);
        employee.setActive(true);
        employee.setRoles(List.of(employeeRole));
        employee.setId(1L);

        return SecurityMockMvcRequestPostProcessors.user(
                new User(employee.getUsername(),
                        new BCryptPasswordEncoder().encode(empPassword),
                        List.of(new SimpleGrantedAuthority(employee.getRoles().toArray()[0].toString()))));
    }
}
