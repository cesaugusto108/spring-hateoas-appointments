package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface PatientService {
    Page<Patient> findAll(int page, int size, Sort.Direction direction, String field);

    Patient getPatient(Long id);

    Patient savePatient(Patient patient);

    Page<Patient> findPatientByNameLikeOrEmailLike(String search, int page, int size, Sort.Direction direction, String field);
}
