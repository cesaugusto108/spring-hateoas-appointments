package augusto108.ces.appointmenttracker.model.entities;

import augusto108.ces.appointmenttracker.model.enums.Specialty;
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
@Table(name = "tb_physician")
public class Physician extends Person
{

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "tb_physician_appointment",
		joinColumns = @JoinColumn(name = "physician_id"),
		inverseJoinColumns = @JoinColumn(name = "appointment_id")
	)
	private final Set<Appointment> appointments = new HashSet<>();
	@Setter
	@Enumerated(value = EnumType.STRING)
	@Column(name = "specialty")
	private Specialty specialty;

	@Override
	public String toString()
	{
		return getFirstName() + " " + getLastName() + " (" + getSpecialty().toString() + ")";
	}
}
