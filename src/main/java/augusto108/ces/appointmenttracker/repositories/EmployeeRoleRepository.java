package augusto108.ces.appointmenttracker.repositories;

import augusto108.ces.appointmenttracker.security.entities.EmployeeRole;
import augusto108.ces.appointmenttracker.security.enums.Role;

public interface EmployeeRoleRepository {

    EmployeeRole getEmployeeRolebyRole(Role role);

    void saveEmployeeRole(EmployeeRole employeeRole);
}
