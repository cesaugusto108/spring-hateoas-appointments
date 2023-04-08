package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.exceptions.EntityNotFoundException;
import augusto108.ces.appointmenttracker.model.Patient;
import augusto108.ces.appointmenttracker.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository repository;

    @Override
    public Page<Patient> getPatients(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Patient getPatient(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found. Id: " + id));
    }

    @Override
    public Patient savePatient(Patient patient) {
        return repository.save(patient);
    }
}
