package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.model.Appointment;
import org.springframework.data.domain.Page;

public interface AppointmentService {
    Appointment getAppointment(Long id);

    Appointment saveAppointment(Appointment appointment);

    Page<Appointment> findAppointmentByStatusOrPersonName(String search, int page, int size);
}
