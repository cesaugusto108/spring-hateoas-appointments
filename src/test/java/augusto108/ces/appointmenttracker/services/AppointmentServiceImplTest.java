package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.model.Appointment;
import augusto108.ces.appointmenttracker.model.enums.Status;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
class AppointmentServiceImplTest {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PhysicianService physicianService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void findAll() {
        final Page<Appointment> appointments = appointmentService.findAll(0, 20, Sort.Direction.ASC, "id");

        assertEquals(20, appointments.getTotalElements());
        assertEquals("Patient: Antonio Nunes (antonio@email.com) | " +
                        "Physician: João Brito (DERMATOLOGIST) | " +
                        "CONFIRMED",
                appointments.get().toList().get(19).toString());
    }

    @Test
    void getAppointment() {
        final Appointment appointment = appointmentService
                .getAppointment(1L);

        assertEquals("Patient: Pedro Cardoso (pedro@email.com) | " +
                        "Physician: João Brito (DERMATOLOGIST) | " +
                        "PAYMENT_PENDING",
                appointment.toString());
    }

    @Test
    void saveAppointment() {
        final Appointment appointment = new Appointment();
        appointment.setPatient(patientService.getPatient(2L));
        appointment.setPhysician(physicianService.getPhysician(4L));
        appointment.setStatus(Status.FINISHED);

        appointmentService.saveAppointment(appointment);

        final List<Appointment> appointments = entityManager
                .createQuery("from Appointment order by id", Appointment.class)
                .getResultList();

        assertEquals(21, appointments.size());
        assertEquals("Patient: Paula Martins (paula@email.com) | " +
                        "Physician: Osvaldo Pereira (CARDIOLOGIST) | " +
                        "FINISHED",
                appointments.get(20).toString());
    }

    @Test
    void findAppointmentByStatusOrPersonName() {
        final Page<Appointment> appointmentsByStatus = appointmentService
                .findAppointmentByStatusOrPersonName("FINISHED", 0, 10, Sort.Direction.ASC, "id");

        assertEquals(5, appointmentsByStatus.getTotalElements());
        assertEquals(3, appointmentsByStatus.get().toList().get(0).getId());

        final Page<Appointment> appointmentsByPersonName = appointmentService
                .findAppointmentByStatusOrPersonName("Pedro", 0, 10, Sort.Direction.ASC, "id");

        assertEquals(2, appointmentsByPersonName.getTotalElements());
        assertEquals("Patient: Pedro Cardoso (pedro@email.com) | " +
                        "Physician: João Brito (DERMATOLOGIST) | " +
                        "PAYMENT_PENDING",
                appointmentsByPersonName.get().toList().get(0).toString());
    }
}