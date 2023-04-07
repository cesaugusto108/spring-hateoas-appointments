package augusto108.ces.appointmenttracker.model;

import augusto108.ces.appointmenttracker.model.enums.Specialty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode(callSuper = true, of = {})
@Entity
@Table(name = "tb_physician")
public class Physician extends Person {
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_physician_appointment",
            joinColumns = @JoinColumn(name = "physician_id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_id")
    )
    private final Set<Appointment> appointments = new HashSet<>();

    @Setter
    @Enumerated(value = EnumType.STRING)
    private Specialty specialty;
}
