package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.model.entities.Physician;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface PhysicianService
{

	Page<Physician> findAll(int page, int size, Sort.Direction direction, String field);

	Physician getPhysician(Long id);

	Physician savePhysician(Physician physician);

	Page<Physician> findPhysicianByNameLikeOrSpecialtyLike(String search, int page, int size, Sort.Direction direction, String field);
}
