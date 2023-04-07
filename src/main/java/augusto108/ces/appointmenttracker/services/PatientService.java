package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.model.Patient;
import org.springframework.data.domain.Page;

public interface PatientService {
    Page<Patient> getPatients(int page, int size);

    Patient getPatient(Long id);
}
