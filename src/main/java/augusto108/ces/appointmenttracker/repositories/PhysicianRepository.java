package augusto108.ces.appointmenttracker.repositories;

import augusto108.ces.appointmenttracker.model.Physician;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhysicianRepository extends JpaRepository<Physician, Long> {
    @Query(
            "from Physician p where " +
                    "lower(first_name) like lower(concat('%', :searchStr, '%')) or " +
                    "lower(last_name) like lower(concat('%', :searchStr, '%')) or " +
                    "lower(specialty) like lower(concat('%', :searchStr, '%'))"
    )
    Page<Physician> findPhysicianByNameLikeOrSpecialtyLike(@Param("searchStr") String searchStr, Pageable pageable);
}
