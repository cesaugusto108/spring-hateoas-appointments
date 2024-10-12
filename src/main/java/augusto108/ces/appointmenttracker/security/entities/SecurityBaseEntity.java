package augusto108.ces.appointmenttracker.security.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@MappedSuperclass
public abstract class SecurityBaseEntity
{

	@Id
	@SequenceGenerator(name = "sec_seq_generator", initialValue = 1001, allocationSize = 1)
	@GeneratedValue(generator = "sec_seq_generator", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
}
