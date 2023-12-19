package augusto108.ces.appointmenttracker.repositories;

import augusto108.ces.appointmenttracker.security.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final EntityManager entityManager;

    @Override
    public Employee findEmployeeByUsername(String username) {
        return entityManager
                .createQuery("from Employee e where e.username = :username", Employee.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public void saveEmployee(Employee employee) {
        entityManager.persist(employee);
    }
}
