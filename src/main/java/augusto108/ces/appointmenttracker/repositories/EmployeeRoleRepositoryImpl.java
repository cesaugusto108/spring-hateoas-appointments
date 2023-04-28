package augusto108.ces.appointmenttracker.repositories;

import augusto108.ces.appointmenttracker.security.EmployeeRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class EmployeeRoleRepositoryImpl implements EmployeeRoleRepository {
    private final EntityManager entityManager;

    @Override
    public void saveEmployeeRole(EmployeeRole employeeRole) {
        entityManager.persist(employeeRole);
    }
}
