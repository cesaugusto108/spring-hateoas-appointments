package augusto108.ces.appointmenttracker.services;

import augusto108.ces.appointmenttracker.model.Appointment;
import org.springframework.data.domain.Page;

public interface AppointmentService {
    Page<Appointment> getAppointments(int page, int size);

    Appointment getAppointment(Long id);

    Appointment saveAppointment(Appointment appointment);
}
