package augusto108.ces.appointmenttracker.assemblers;

import augusto108.ces.appointmenttracker.controllers.AppointmentController;
import augusto108.ces.appointmenttracker.model.Appointment;
import augusto108.ces.appointmenttracker.model.enums.Status;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AppointmentEntityModelAssembler implements RepresentationModelAssembler<Appointment, EntityModel<Appointment>> {
    @Override
    public EntityModel<Appointment> toModel(Appointment entity) {
        final Link self = linkTo(methodOn(AppointmentController.class).getAppointmentById(entity.getId())).withSelfRel();
        final Link appointments = linkTo(methodOn(AppointmentController.class).getAppointments(0, 5, Sort.Direction.ASC, "")).withRel("appointments");
        final Link search = linkTo(methodOn(AppointmentController.class).searchAppointments("", 0, 5, Sort.Direction.ASC, "")).withRel("search");
        final Link confirm = linkTo(methodOn(AppointmentController.class).confirmAppointment(entity.getId())).withRel("confirm");
        final Link cancel = linkTo(methodOn(AppointmentController.class).cancelAppointment(entity.getId())).withRel("cancel");
        final Link finish = linkTo(methodOn(AppointmentController.class).finishAppointment(entity.getId())).withRel("finish");

        final List<Link> commonLinks = Arrays.asList(self, appointments, search);

        if (entity.getStatus() == Status.PAYMENT_PENDING) {
            commonLinks.add(confirm);
            commonLinks.add(cancel);
        }

        if (entity.getStatus() == Status.CONFIRMED) {
            commonLinks.add(cancel);
            commonLinks.add(finish);
        }

        return EntityModel.of(entity, commonLinks);
    }
}
