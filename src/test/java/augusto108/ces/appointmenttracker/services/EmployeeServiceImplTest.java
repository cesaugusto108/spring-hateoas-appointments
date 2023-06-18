package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.security.Employee;
import augusto108.ces.appointmenttracker.security.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("sec")
@Transactional
class EmployeeServiceImplTest {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRoleService employeeRoleService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void findEmployeeByUsername() {
        final Employee employee = employeeService.findEmployeeByUsername("monteiro");

        assertEquals(1005, employee.getId());
    }

    @Test
    void saveEmployee() {
        final Employee employee = new Employee();
        employee.setActive(true);
        employee.setPassword("1234");
        employee.setUsername("batista");

        employeeService.saveEmployee(employee);

        final List<Employee> employees = entityManager
                .createQuery("from Employee e order by id", Employee.class)
                .getResultList();

        assertEquals(5, employees.size());
        assertEquals("batista", employees.get(4).getUsername());
    }

    @Test
    void loadUserByUsername() {
        final Employee employee = new Employee();
        employee.setActive(true);
        employee.setPassword("1234");
        employee.setUsername("sampaio");
        employee.setRoles(
                List.of(employeeRoleService.getEmployeeRoleByRole(Role.ROLE_EMPLOYEE),
                        employeeRoleService.getEmployeeRoleByRole(Role.ROLE_MANAGER))
        );

        employeeService.saveEmployee(employee);

        final UserDetails userDetails = employeeService.loadUserByUsername("sampaio");

        assertEquals("sampaio", userDetails.getUsername());
        assertEquals("1234", userDetails.getPassword());
        assertEquals(2, userDetails.getAuthorities().size());
        assertEquals("ROLE_EMPLOYEE", userDetails.getAuthorities().toArray()[0].toString());
        assertEquals("ROLE_MANAGER", userDetails.getAuthorities().toArray()[1].toString());
    }
}