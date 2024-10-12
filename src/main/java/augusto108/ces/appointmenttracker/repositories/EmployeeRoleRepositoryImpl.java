package augusto108.ces.appointmenttracker.repositories;

import augusto108.ces.appointmenttracker.security.entities.EmployeeRole;
import augusto108.ces.appointmenttracker.security.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class EmployeeRoleRepositoryImpl implements EmployeeRoleRepository
{

	private final EntityManager entityManager;

	@Override
	public EmployeeRole getEmployeeRolebyRole(Role role)
	{
		return entityManager
			.createQuery("from EmployeeRole e where e.role = :role", EmployeeRole.class)
			.setParameter("role", role)
			.getSingleResult();
	}

	@Override
	public void saveEmployeeRole(EmployeeRole employeeRole)
	{
		entityManager.persist(employeeRole);
	}
}
