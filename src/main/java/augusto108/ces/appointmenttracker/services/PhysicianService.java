package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.model.Physician;
import org.springframework.data.domain.Page;

public interface PhysicianService {
    Physician getPhysician(Long id);

    Physician savePhysician(Physician physician);

    Page<Physician> findPhysicianByNameLikeOrSpecialtyLike(String search, int page, int size);
}
