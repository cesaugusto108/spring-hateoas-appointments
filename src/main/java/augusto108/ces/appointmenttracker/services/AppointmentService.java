package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.model.entities.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface AppointmentService {

    Page<Appointment> findAll(int page, int size, Sort.Direction direction, String field);

    Appointment getAppointment(Long id);

    Appointment saveAppointment(Appointment appointment);

    Page<Appointment> findAppointmentByStatusOrPersonName(String search, int page, int size, Sort.Direction direction, String field);
}
