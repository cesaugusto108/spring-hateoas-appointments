package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.security.Employee;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface EmployeeService extends UserDetailsService {
    Employee findEmployeeByUsername(String username);

    void saveEmployee(Employee employee);
}
