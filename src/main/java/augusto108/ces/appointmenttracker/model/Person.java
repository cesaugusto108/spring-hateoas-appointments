package augusto108.ces.appointmenttracker.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
@MappedSuperclass
public abstract class Person extends BaseEntity {
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;
}
