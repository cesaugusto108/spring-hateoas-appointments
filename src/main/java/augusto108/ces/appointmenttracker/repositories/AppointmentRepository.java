package augusto108.ces.appointmenttracker.repositories;

import augusto108.ces.appointmenttracker.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
