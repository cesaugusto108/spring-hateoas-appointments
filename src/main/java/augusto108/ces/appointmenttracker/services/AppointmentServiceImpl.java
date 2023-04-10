package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.exceptions.EntityNotFoundException;
import augusto108.ces.appointmenttracker.model.Appointment;
import augusto108.ces.appointmenttracker.repositories.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository repository;

    @Override
    public Page<Appointment> findAll(int page, int size, Sort.Direction direction, String field) {
        Sort sortCriteria = Sort.by(direction, field);

        return repository.findAll(PageRequest.of(page, size, sortCriteria));
    }

    @Override
    public Appointment getAppointment(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found. Id: " + id));
    }

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        return repository.save(appointment);
    }

    @Override
    public Page<Appointment> findAppointmentByStatusOrPersonName(String search, int page, int size, Sort.Direction direction, String field) {
        Sort sortCriteria = Sort.by(direction, field);

        return repository.findAppointmentByStatusOrPersonName(search, PageRequest.of(page, size, sortCriteria));
    }
}
