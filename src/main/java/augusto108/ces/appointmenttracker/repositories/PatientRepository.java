package augusto108.ces.appointmenttracker.repositories;

import augusto108.ces.appointmenttracker.model.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query(
            "from Patient p where " +
                    "lower(first_name) like lower(concat('%', :searchStr, '%')) or " +
                    "lower(last_name) like lower(concat('%', :searchStr, '%')) or " +
                    "lower(email) like lower(concat('%', :searchStr, '%'))"
    )
    Page<Patient> findPatientByNameLikeOrEmailLike(@Param("searchStr") String searchStr, Pageable pageable);
}
