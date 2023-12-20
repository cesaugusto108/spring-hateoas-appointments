package augusto108.ces.appointmenttracker.repositories;

import augusto108.ces.appointmenttracker.security.entities.Employee;

public interface EmployeeRepository {

    Employee findEmployeeByUsername(String username);

    void saveEmployee(Employee employee);
}
