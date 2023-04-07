package augusto108.ces.appointmenttracker.repositories;

import augusto108.ces.appointmenttracker.model.Physician;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhysicianRepository extends JpaRepository<Physician, Long> {

}
