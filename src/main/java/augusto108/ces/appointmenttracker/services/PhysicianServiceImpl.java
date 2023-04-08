package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.exceptions.EntityNotFoundException;
import augusto108.ces.appointmenttracker.model.Physician;
import augusto108.ces.appointmenttracker.repositories.PhysicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhysicianServiceImpl implements PhysicianService {
    private final PhysicianRepository repository;

    @Override
    public Page<Physician> getPhysicians(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Physician getPhysician(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found. Id: " + id));
    }

    @Override
    public Physician savePhysician(Physician physician) {
        return repository.save(physician);
    }
}
