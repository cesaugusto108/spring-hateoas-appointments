package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.model.entities.Patient;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
class PatientServiceImplTest
{

	private final PatientService patientService;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired PatientServiceImplTest(PatientService patientService)
	{
		this.patientService = patientService;
	}

	@BeforeEach
	void setUp()
	{
		final String query1 = "INSERT INTO `tb_patient` (`id`, `first_name`, `last_name`, `email`)\n" +
			"    VALUES (1, 'Pedro', 'Cardoso', 'pedro@email.com');";

		final String query2 = "INSERT INTO `tb_patient` (`id`, `first_name`, `last_name`, `email`)\n" +
			"    VALUES (2, 'Paula', 'Martins', 'paula@email.com');";

		entityManager.createNativeQuery(query1).executeUpdate();
		entityManager.createNativeQuery(query2).executeUpdate();
	}

	@AfterEach
	void tearDown()
	{
		entityManager.createNativeQuery("delete from `tb_patient`;").executeUpdate();
	}

	@Test
	void findAll()
	{
		final Page<Patient> patientsFirstPage = patientService.findAll(0, 10, Sort.Direction.ASC, "id");
		assertEquals(2, patientsFirstPage.getTotalElements());
		assertEquals(1, patientsFirstPage.getTotalPages());
		assertEquals("Pedro Cardoso (pedro@email.com)", patientsFirstPage.get().toList().get(0).toString());
	}

	@Test
	void getPatient()
	{
		final Patient patient = patientService.getPatient(1L);
		assertEquals("Pedro Cardoso (pedro@email.com)", patient.toString());
	}

	@Test
	void savePatient()
	{
		final Patient patient = new Patient();
		patient.setFirstName("Tiago");
		patient.setLastName("Siqueira");
		patient.setEmail("tiago.siq@email.com");

		patientService.savePatient(patient);

		final List<Patient> patients = entityManager
			.createQuery("from Patient order by id", Patient.class)
			.getResultList();

		assertEquals(3, patients.size());
		assertEquals("Tiago Siqueira (tiago.siq@email.com)", patients.get(2).toString());
	}

	@Test
	void findPatientByNameLikeOrEmailLike()
	{
		final Page<Patient> patientsByName = patientService
			.findPatientByNameLikeOrEmailLike("Cardoso", 0, 10, Sort.Direction.ASC, "id");

		assertEquals(1, patientsByName.getTotalElements());
		assertEquals(1, patientsByName.get().toList().get(0).getId());
		assertEquals("Pedro Cardoso (pedro@email.com)", patientsByName.get().toList().get(0).toString());

		final Page<Patient> patientsByEmail = patientService
			.findPatientByNameLikeOrEmailLike("paula@email", 0, 10, Sort.Direction.ASC, "id");

		assertEquals(1, patientsByEmail.getTotalElements());
		assertEquals(2, patientsByEmail.get().toList().get(0).getId());
		assertEquals("Paula Martins (paula@email.com)", patientsByEmail.get().toList().get(0).toString());
	}
}
