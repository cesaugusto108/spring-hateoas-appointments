package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.model.entities.Appointment;
import augusto108.ces.appointmenttracker.model.enums.Status;
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
class AppointmentServiceImplTest {

    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final PhysicianService physicianService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    AppointmentServiceImplTest(AppointmentService appointmentService,
                               PatientService patientService,
                               PhysicianService physicianService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.physicianService = physicianService;
    }

    @BeforeEach
    void setUp() {
        final String query1 = "INSERT INTO `tb_patient` (`id`, `first_name`, `last_name`, `email`)\n" +
                "    VALUES (1, 'Pedro', 'Cardoso', 'pedro@email.com');";

        final String query2 = "INSERT INTO `tb_patient` (`id`, `first_name`, `last_name`, `email`)\n" +
                "    VALUES (2, 'Paula', 'Martins', 'paula@email.com');";

        final String query3 = "INSERT INTO `tb_physician` (`id`, `first_name`, `last_name`, `specialty`)\n" +
                "    VALUES (1, 'Marcela', 'Cavalcante', 'GENERAL_PRACTITIONER');";

        final String query4 = "INSERT INTO `tb_physician` (`id`, `first_name`, `last_name`, `specialty`)\n" +
                "    VALUES (2, 'João', 'Cavalcante', 'DERMATOLOGIST');";

        final String query5 = "INSERT INTO `tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)\n" +
                "    VALUES (1, 1, 2, 'PAYMENT_PENDING');";

        final String query6 = "INSERT INTO `tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)\n" +
                "    VALUES (2, 2, 1, 'CONFIRMED');";

        entityManager.createNativeQuery(query1).executeUpdate();
        entityManager.createNativeQuery(query2).executeUpdate();
        entityManager.createNativeQuery(query3).executeUpdate();
        entityManager.createNativeQuery(query4).executeUpdate();
        entityManager.createNativeQuery(query5).executeUpdate();
        entityManager.createNativeQuery(query6).executeUpdate();
    }

    @AfterEach
    void tearDown() {
        entityManager.createNativeQuery("delete from `tb_appointment`;").executeUpdate();
        entityManager.createNativeQuery("delete from `tb_physician`;").executeUpdate();
        entityManager.createNativeQuery("delete from `tb_patient`;").executeUpdate();
    }

    @Test
    void findAll() {
        final Page<Appointment> appointments = appointmentService.findAll(0, 20, Sort.Direction.ASC, "id");

        assertEquals(2, appointments.getTotalElements());
        assertEquals("Patient: Paula Martins (paula@email.com) | " +
                        "Physician: Marcela Cavalcante (GENERAL_PRACTITIONER) | CONFIRMED",
                appointments.get().toList().get(1).toString());
    }

    @Test
    void getAppointment() {
        final Appointment appointment = appointmentService
                .getAppointment(2L);

        assertEquals("Patient: Paula Martins (paula@email.com) | " +
                "Physician: Marcela Cavalcante (GENERAL_PRACTITIONER) | CONFIRMED", appointment.toString());
    }

    @Test
    void saveAppointment() {
        final Appointment appointment = new Appointment();
        appointment.setPatient(patientService.getPatient(2L));
        appointment.setPhysician(physicianService.getPhysician(1L));
        appointment.setStatus(Status.FINISHED);

        appointmentService.saveAppointment(appointment);

        final List<Appointment> appointments = entityManager
                .createQuery("from Appointment order by id", Appointment.class)
                .getResultList();

        assertEquals(3, appointments.size());
        assertEquals("Patient: Paula Martins (paula@email.com) | " +
                "Physician: Marcela Cavalcante (GENERAL_PRACTITIONER) | FINISHED", appointments.get(2).toString());
    }

    @Test
    void findAppointmentByStatusOrPersonName() {
        final Page<Appointment> appointmentsByStatus = appointmentService
                .findAppointmentByStatusOrPersonName("CONFIRMED", 0, 10, Sort.Direction.ASC, "id");

        assertEquals(1, appointmentsByStatus.getTotalElements());
        assertEquals(2, appointmentsByStatus.get().toList().get(0).getId());

        final Page<Appointment> appointmentsByPersonName = appointmentService
                .findAppointmentByStatusOrPersonName("Paula", 0, 10, Sort.Direction.ASC, "id");

        assertEquals(1, appointmentsByPersonName.getTotalElements());
        assertEquals("Patient: Paula Martins (paula@email.com) | " +
                        "Physician: Marcela Cavalcante (GENERAL_PRACTITIONER) | CONFIRMED",
                appointmentsByPersonName.get().toList().get(0).toString());
    }
}