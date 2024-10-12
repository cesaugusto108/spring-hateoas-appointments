package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.security.entities.EmployeeRole;
import augusto108.ces.appointmenttracker.security.enums.Role;

public interface EmployeeRoleService
{

	EmployeeRole getEmployeeRoleByRole(Role role);

	void saveEmployeeRole(EmployeeRole employeeRole);
}
