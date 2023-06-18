package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.model.Patient;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
class PatientServiceImplTest {
    @Autowired
    private PatientService patientService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void findAll() {
        final Page<Patient> patientsFirstPage = patientService.findAll(0, 10, Sort.Direction.ASC, "id");

        assertEquals(12, patientsFirstPage.getTotalElements());
        assertEquals(2, patientsFirstPage.getTotalPages());
        assertEquals("José Santos (jose@email.com)", patientsFirstPage.get().toList().get(9).toString());
    }

    @Test
    void getPatient() {
        final Patient patient = patientService.getPatient(12L);
        assertEquals("Pedro Siqueira (pedro.s@email.com)", patient.toString());
    }

    @Test
    void savePatient() {
        final Patient patient = new Patient();
        patient.setFirstName("Tiago");
        patient.setLastName("Siqueira");
        patient.setEmail("tiago.siq@email.com");

        patientService.savePatient(patient);

        final List<Patient> patients = entityManager
                .createQuery("from Patient order by id", Patient.class)
                .getResultList();

        assertEquals(13, patients.size());
        assertEquals("Tiago Siqueira (tiago.siq@email.com)", patients.get(12).toString());
    }

    @Test
    void findPatientByNameLikeOrEmailLike() {
        final Page<Patient> patientsByName = patientService
                .findPatientByNameLikeOrEmailLike("Tavares", 0, 10, Sort.Direction.ASC, "id");

        assertEquals(1, patientsByName.getTotalElements());
        assertEquals(11, patientsByName.get().toList().get(0).getId());
        assertEquals("Ana Tavares (ana@email.com)", patientsByName.get().toList().get(0).toString());

        final Page<Patient> patientsByEmail = patientService
                .findPatientByNameLikeOrEmailLike("beatriz@email", 0, 10, Sort.Direction.ASC, "id");

        assertEquals(1, patientsByEmail.getTotalElements());
        assertEquals(6, patientsByEmail.get().toList().get(0).getId());
        assertEquals("Beatriz Silva (beatriz@email.com)", patientsByEmail.get().toList().get(0).toString());
    }
}