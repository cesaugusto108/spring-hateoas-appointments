package augusto108.ces.appointmenttracker.assemblers;

import augusto108.ces.appointmenttracker.controllers.AppointmentController;
import augusto108.ces.appointmenttracker.helpers.DefaultParameterObj;
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

    private final DefaultParameterObj param = new DefaultParameterObj();

    @Override
    public EntityModel<Appointment> toModel(Appointment entity) {
        final int page = param.getPage();
        final int size = param.getSize();
        final Sort.Direction direction = param.getDirection();
        final String field = param.getField();

        final Link self = linkTo(methodOn(AppointmentController.class).getAppointmentById(entity.getId())).withSelfRel();
        final Link appointments = linkTo(methodOn(AppointmentController.class).getAppointments(page, size, direction, field)).withRel("appointments");
        final Link search = linkTo(methodOn(AppointmentController.class).searchAppointments("", page, size, direction, field)).withRel("search");
        final Link confirm = linkTo(methodOn(AppointmentController.class).confirmAppointment(entity.getId())).withRel("confirm");
        final Link cancel = linkTo(methodOn(AppointmentController.class).cancelAppointment(entity.getId())).withRel("cancel");
        final Link finish = linkTo(methodOn(AppointmentController.class).finishAppointment(entity.getId())).withRel("finish");

        final List<Link> commonLinks = Arrays.asList(self, appointments, search);
        final List<Link> paymentPendingLinks = Arrays.asList(self, confirm, cancel, appointments, search);
        final List<Link> confirmedLinks = Arrays.asList(self, cancel, finish, appointments, search);

        if (entity.getStatus() == Status.PAYMENT_PENDING) return EntityModel.of(entity, paymentPendingLinks);
        if (entity.getStatus() == Status.CONFIRMED) return EntityModel.of(entity, confirmedLinks);
        return EntityModel.of(entity, commonLinks);
    }
}
