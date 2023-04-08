package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.model.Patient;
import org.springframework.data.domain.Page;

public interface PatientService {
    Patient getPatient(Long id);

    Patient savePatient(Patient patient);

    Page<Patient> findPatientByNameLikeOrEmailLike(String search, int page, int size);
}
