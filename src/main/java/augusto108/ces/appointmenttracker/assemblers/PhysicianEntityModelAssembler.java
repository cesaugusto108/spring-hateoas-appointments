package augusto108.ces.appointmenttracker.assemblers;

import augusto108.ces.appointmenttracker.controllers.PhysicianController;
import augusto108.ces.appointmenttracker.controllers.helpers.DefaultParameterObj;
import augusto108.ces.appointmenttracker.model.Physician;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PhysicianEntityModelAssembler implements RepresentationModelAssembler<Physician, EntityModel<Physician>> {
    private final DefaultParameterObj param = new DefaultParameterObj();

    @Override
    public EntityModel<Physician> toModel(Physician entity) {
        final int page = param.getPage();
        final int size = param.getSize();
        final Sort.Direction direction = param.getDirection();
        final String field = param.getField();

        return EntityModel.of(
                entity,
                linkTo(methodOn(PhysicianController.class).getPhysicianById(entity.getId())).withSelfRel(),
                linkTo(methodOn(PhysicianController.class).getPhysicians(page, size, direction, field)).withRel("physicians"),
                linkTo(methodOn(PhysicianController.class).searchPhysicians("", page, size, direction, field)).withRel("search")
        );
    }
}
