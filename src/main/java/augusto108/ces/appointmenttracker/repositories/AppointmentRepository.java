package augusto108.ces.appointmenttracker.repositories;

import augusto108.ces.appointmenttracker.model.entities.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>
{

	@Query(
		"from Appointment a where " +
			"lower(a.status) like lower(concat('%', :searchStr, '%')) or " +
			"lower(a.patient.firstName) like lower(concat('%', :searchStr, '%')) or " +
			"lower(a.patient.lastName) like lower(concat('%', :searchStr, '%')) or " +
			"lower(a.physician.firstName) like lower(concat('%', :searchStr, '%')) or " +
			"lower(a.physician.lastName) like lower(concat('%', :searchStr, '%'))"
	)
	Page<Appointment> findAppointmentByStatusOrPersonName(@Param("searchStr") String searchStr, Pageable pageable);
}
