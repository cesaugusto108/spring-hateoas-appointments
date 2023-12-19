package augusto108.ces.appointmenttracker.model.representations;

import augusto108.ces.appointmenttracker.model.entities.Appointment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
public class PatientModel extends RepresentationModel<PatientModel> {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    @JsonIgnoreProperties("patient")
    private Set<Appointment> appointments = new HashSet<>();
}
