package augusto108.ces.appointmenttracker.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode(callSuper = true, of = {})
@Entity
@Table(name = "tb_patient")
public class Patient extends Person {

    @Setter
    @Column(name = "email", length = 100)
    private String email;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_patient_appointment",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_id")
    )
    private final Set<Appointment> appointments = new HashSet<>();

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " (" + getEmail() + ")";
    }
}
