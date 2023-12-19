package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.model.entities.Physician;
import augusto108.ces.appointmenttracker.model.enums.Specialty;
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
class PhysicianServiceImplTest {
    @Autowired
    private PhysicianService physicianService;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        final String query1 = "INSERT INTO `tb_physician` (`id`, `first_name`, `last_name`, `specialty`)\n" +
                "    VALUES (1, 'Marcela', 'Cavalcante', 'GENERAL_PRACTITIONER');";

        final String query2 = "INSERT INTO `tb_physician` (`id`, `first_name`, `last_name`, `specialty`)\n" +
                "    VALUES (2, 'João', 'Cavalcante', 'DERMATOLOGIST');";

        entityManager.createNativeQuery(query1).executeUpdate();
        entityManager.createNativeQuery(query2).executeUpdate();
    }

    @AfterEach
    void tearDown() {
        entityManager.createNativeQuery("delete from `tb_physician`;").executeUpdate();
    }

    @Test
    void findAll() {
        final Page<Physician> physicians = physicianService.findAll(0, 10, Sort.Direction.ASC, "id");

        assertEquals(2, physicians.getTotalElements());
        assertEquals("Marcela Cavalcante (GENERAL_PRACTITIONER)", physicians.get().toList().get(0).toString());
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

        assertEquals(3, physicians.size());
        assertEquals("Mardoqueu Santos (PSYCHIATRIST)", physicians.get(2).toString());
    }

    @Test
    void findPhysicianByNameLikeOrSpecialtyLike() {
        final Page<Physician> physiciansByName = physicianService
                .findPhysicianByNameLikeOrSpecialtyLike("Cavalcante", 0, 10, Sort.Direction.ASC, "id");

        assertEquals(2, physiciansByName.getTotalElements());
        assertEquals("Marcela Cavalcante (GENERAL_PRACTITIONER)", physiciansByName.get().toList().get(0).toString());
        assertEquals("João Cavalcante (DERMATOLOGIST)", physiciansByName.get().toList().get(1).toString());

        final Page<Physician> physiciansBySpecialty = physicianService
                .findPhysicianByNameLikeOrSpecialtyLike("DERMATOLOGIST", 0, 10, Sort.Direction.DESC, "firstName");

        assertEquals(1, physiciansBySpecialty.getTotalElements());
        assertEquals("João Cavalcante (DERMATOLOGIST)", physiciansBySpecialty.get().toList().get(0).toString());
    }
}