package augusto108.ces.appointmenttracker.security.entities;

import augusto108.ces.appointmenttracker.security.enums.Role;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_role")
public final class EmployeeRole extends SecurityBaseEntity
{

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, unique = true, length = 50)
	private Role role;

	@Override
	public String toString()
	{
		return role.toString();
	}
}
