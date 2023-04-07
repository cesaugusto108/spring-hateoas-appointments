package augusto108.ces.appointmenttracker.repositories;

import augusto108.ces.appointmenttracker.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
