package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.model.Appointment;
import augusto108.ces.appointmenttracker.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository repository;

    @Override
    public Page<Appointment> getAppointments(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Appointment getAppointment(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
