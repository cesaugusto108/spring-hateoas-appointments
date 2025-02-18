package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.repositories.EmployeeRoleRepository;
import augusto108.ces.appointmenttracker.security.entities.EmployeeRole;
import augusto108.ces.appointmenttracker.security.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeRoleServiceImpl implements EmployeeRoleService
{

	private final EmployeeRoleRepository repository;

	@Override
	public EmployeeRole getEmployeeRoleByRole(Role role)
	{
		return repository.getEmployeeRolebyRole(role);
	}

	@Override
	public void saveEmployeeRole(EmployeeRole employeeRole)
	{
		repository.saveEmployeeRole(employeeRole);
	}
}
