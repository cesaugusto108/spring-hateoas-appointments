package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.security.EmployeeRole;
import augusto108.ces.appointmenttracker.security.enums.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setUp() {
        final String query1 = "INSERT INTO `employee` (`id`, `active`, `password`, `username`)\n" +
                "    VALUES (9002, 1, '1234', 'santos');";

        final String query2 = "INSERT INTO `user_role` (`id`, `role`)\n" +
                "    VALUES (10002, 'ROLE_ADMIN');";

        final String query3 = "INSERT INTO `employees_roles` (`employee_id`, `role_id`)\n" +
                "    VALUES (9002, 10002);";

        entityManager.createNativeQuery(query1).executeUpdate();
        entityManager.createNativeQuery(query2).executeUpdate();
        entityManager.createNativeQuery(query3).executeUpdate();
    }

    @AfterEach
    void tearDown() {
        entityManager.createNativeQuery("delete from `employees_roles`;");
        entityManager.createNativeQuery("delete from `employee`;");
        entityManager.createNativeQuery("delete from `user_role`;");
    }

    @Test
    void getEmployeeRoleByRole() {
        final EmployeeRole employeeRole = employeeRoleService.getEmployeeRoleByRole(Role.ROLE_ADMIN);
        assertEquals("ROLE_ADMIN", employeeRole.getRole().toString());
        assertEquals(10002, employeeRole.getId());
    }

    @Test
    void saveEmployeeRole() {
        final EmployeeRole role = new EmployeeRole(Role.ROLE_TEST);
        employeeRoleService.saveEmployeeRole(role);
        final EmployeeRole r = employeeRoleService.getEmployeeRoleByRole(Role.ROLE_TEST);
        assertEquals("ROLE_TEST", r.getRole().toString());
        assertEquals(1001, r.getId());
    }
}