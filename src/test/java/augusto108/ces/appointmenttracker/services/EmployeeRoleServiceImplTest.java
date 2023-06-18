package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.security.EmployeeRole;
import augusto108.ces.appointmenttracker.security.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("sec")
@Transactional
class EmployeeRoleServiceImplTest {
    @Autowired
    private EmployeeRoleService employeeRoleService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void getEmployeeRoleByRole() {
        final EmployeeRole employeeRole = employeeRoleService.getEmployeeRoleByRole(Role.ROLE_ADMIN);

        assertEquals("ROLE_ADMIN", employeeRole.getRole().toString());
        assertEquals(1003, employeeRole.getId());
    }

    @Test
    void saveEmployeeRole() {
        final EmployeeRole role = new EmployeeRole(Role.ROLE_TEST);
        employeeRoleService.saveEmployeeRole(role);

        final EmployeeRole r = employeeRoleService.getEmployeeRoleByRole(Role.ROLE_TEST);

        assertEquals("ROLE_TEST", r.getRole().toString());
        assertEquals(1009, r.getId());
    }
}