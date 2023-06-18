package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.model.Physician;
import augusto108.ces.appointmenttracker.model.enums.Specialty;
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
class PhysicianServiceImplTest {
    @Autowired
    private PhysicianService physicianService;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void findAll() {
        final Page<Physician> physicians = physicianService.findAll(0, 10, Sort.Direction.ASC, "id");

        assertEquals(10, physicians.getTotalElements());
        assertEquals("Zélia Silva (GENERAL_PRACTITIONER)", physicians.get().toList().get(9).toString());
    }

    @Test
    void getPhysician() {
        final Physician physician = physicianService.getPhysician(1L);
        assertEquals("Marcela Cavalcante (GENERAL_PRACTITIONER)", physician.toString());
    }

    @Test
    void savePhysician() {
        final Physician physician = new Physician();
        physician.setFirstName("Mardoqueu");
        physician.setLastName("Santos");
        physician.setSpecialty(Specialty.PSYCHIATRIST);

        physicianService.savePhysician(physician);

        final List<Physician> physicians = entityManager
                .createQuery("from Physician order by id", Physician.class)
                .getResultList();

        assertEquals(11, physicians.size());
    }

    @Test
    void findPhysicianByNameLikeOrSpecialtyLike() {
        final Page<Physician> physiciansByName = physicianService
                .findPhysicianByNameLikeOrSpecialtyLike("Marcela", 0, 10, Sort.Direction.ASC, "id");

        assertEquals(2, physiciansByName.getTotalElements());
        assertEquals("Marcela Cavalcante (GENERAL_PRACTITIONER)", physiciansByName.get().toList().get(0).toString());
        assertEquals("Marcela Barros (ORTHOPEDIST)", physiciansByName.get().toList().get(1).toString());

        final Page<Physician> physiciansBySpecialty = physicianService
                .findPhysicianByNameLikeOrSpecialtyLike("CARDIOLOGIST", 0, 10, Sort.Direction.DESC, "firstName");

        assertEquals(2, physiciansBySpecialty.getTotalElements());
        assertEquals("Osvaldo Pereira (CARDIOLOGIST)", physiciansBySpecialty.get().toList().get(0).toString());
        assertEquals("Marília Dantas (CARDIOLOGIST)", physiciansBySpecialty.get().toList().get(1).toString());
    }
}