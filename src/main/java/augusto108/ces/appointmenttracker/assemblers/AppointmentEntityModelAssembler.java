package augusto108.ces.appointmenttracker.assemblers;

import augusto108.ces.appointmenttracker.controllers.AppointmentController;
import augusto108.ces.appointmenttracker.model.Appointment;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AppointmentEntityModelAssembler implements RepresentationModelAssembler<Appointment, EntityModel<Appointment>> {
    @Override
    public EntityModel<Appointment> toModel(Appointment entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(AppointmentController.class).getAppointmentById(entity.getId())).withSelfRel(),
                linkTo(methodOn(AppointmentController.class).getAppointments(0, 5, Sort.Direction.ASC, "")).withRel("appointments"),
                linkTo(methodOn(AppointmentController.class).searchAppointments("", 0, 5, Sort.Direction.ASC, "")).withRel("search")
        );
    }
}
