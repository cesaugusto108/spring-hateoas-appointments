package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.security.entities.Employee;
import augusto108.ces.appointmenttracker.security.enums.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
class EmployeeServiceImplTest
{

	private final EmployeeService employeeService;
	private final EmployeeRoleService employeeRoleService;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired EmployeeServiceImplTest(EmployeeService employeeService, EmployeeRoleService employeeRoleService)
	{
		this.employeeService = employeeService;
		this.employeeRoleService = employeeRoleService;
	}

	@BeforeEach
	void setUp()
	{
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
	void tearDown()
	{
		entityManager.createNativeQuery("delete from `employee`;");
	}

	@Test
	void findEmployeeByUsername()
	{
		final Employee employee = employeeService.findEmployeeByUsername("santos");
		assertEquals(9002, employee.getId());
	}

	@Test
	void saveEmployee()
	{
		final Employee employee = new Employee();
		employee.setActive(true);
		employee.setPassword("1234");
		employee.setUsername("batista");

		employeeService.saveEmployee(employee);

		final List<Employee> employees = entityManager
			.createQuery("from Employee e order by id", Employee.class)
			.getResultList();

		assertEquals(2, employees.size());
		assertEquals("batista", employees.get(0).getUsername());
	}

	@Test
	void loadUserByUsername()
	{
		final Employee employee = new Employee();
		employee.setActive(true);
		employee.setPassword("1234");
		employee.setUsername("sampaio");
		employee.setRoles(
			List.of(employeeRoleService.getEmployeeRoleByRole(Role.ROLE_ADMIN))
		);

		employeeService.saveEmployee(employee);

		final UserDetails userDetails = employeeService.loadUserByUsername("sampaio");

		assertEquals("sampaio", userDetails.getUsername());
		assertEquals("1234", userDetails.getPassword());
		assertEquals(1, userDetails.getAuthorities().size());
		assertEquals("ROLE_ADMIN", userDetails.getAuthorities().toArray()[0].toString());
	}
}
