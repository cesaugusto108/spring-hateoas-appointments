package augusto108.ces.appointmenttracker.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@MappedSuperclass
public abstract class BaseEntity
{

	@Id
	@SequenceGenerator(name = "seq_generator", initialValue = 101, allocationSize = 1)
	@GeneratedValue(generator = "seq_generator", strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
}
