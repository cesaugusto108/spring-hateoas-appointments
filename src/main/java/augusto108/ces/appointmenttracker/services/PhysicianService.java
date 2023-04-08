package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.model.Physician;
import org.springframework.data.domain.Page;

public interface PhysicianService {
    Page<Physician> getPhysicians(int page, int size);

    Physician getPhysician(Long id);

    Physician savePhysician(Physician physician);
}
