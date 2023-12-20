package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.exceptions.EntityNotFoundException;
import augusto108.ces.appointmenttracker.model.entities.Patient;
import augusto108.ces.appointmenttracker.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

    @Override
    public Page<Patient> findAll(int page, int size, Sort.Direction direction, String field) {
        final Sort sortCriteria = Sort.by(direction, field);
        return repository.findAll(PageRequest.of(page, size, sortCriteria));
    }

    @Override
    public Patient getPatient(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found. Id: " + id));
    }

    @Override
    public Patient savePatient(Patient patient) {
        return repository.save(patient);
    }

    @Override
    public Page<Patient> findPatientByNameLikeOrEmailLike(String searchStr,
                                                          int page,
                                                          int size,
                                                          Sort.Direction direction,
                                                          String field) {
        final Sort sortCriteria = Sort.by(direction, field);
        return repository.findPatientByNameLikeOrEmailLike(searchStr, PageRequest.of(page, size, sortCriteria));
    }
}
