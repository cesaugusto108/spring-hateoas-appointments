package augusto108.ces.appointmenttracker.model.entities;

import augusto108.ces.appointmenttracker.model.enums.Status;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
@Entity
@Table(name = "tb_appointment")
public class Appointment extends BaseEntity {

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Physician physician;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Override
    public String toString() {
        return "Patient: " + patient.toString() + " | " + "Physician: " + physician.toString() + " | " + status.toString();
    }
}
