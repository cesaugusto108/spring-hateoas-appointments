package augusto108.ces.appointmenttracker.controllers.helpers;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public class DefaultParameterObj {
    private final int page;
    private final int size;
    private final Sort.Direction direction;
    private final String field;

    public DefaultParameterObj() {
        this.page = 0;
        this.size = 5;
        this.direction = Sort.Direction.ASC;
        this.field = "id";
    }
}
