package augusto108.ces.appointmenttracker.repositories;

import augusto108.ces.appointmenttracker.model.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>
{

	@Query(
		"from Patient p where " +
			"lower(p.firstName) like lower(concat('%', :searchStr, '%')) or " +
			"lower(p.lastName) like lower(concat('%', :searchStr, '%')) or " +
			"lower(p.email) like lower(concat('%', :searchStr, '%'))"
	)
	Page<Patient> findPatientByNameLikeOrEmailLike(@Param("searchStr") String searchStr, Pageable pageable);
}
