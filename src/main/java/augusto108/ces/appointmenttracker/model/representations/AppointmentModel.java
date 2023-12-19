package augusto108.ces.appointmenttracker.model.representations;

import augusto108.ces.appointmenttracker.model.entities.Patient;
import augusto108.ces.appointmenttracker.model.entities.Physician;
import augusto108.ces.appointmenttracker.model.enums.Status;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
public class AppointmentModel extends RepresentationModel<AppointmentModel> {

    private Long id;
    private Patient patient;
    private Physician physician;
    private Status status;
}
