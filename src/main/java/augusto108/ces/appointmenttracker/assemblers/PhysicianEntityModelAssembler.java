package augusto108.ces.appointmenttracker.assemblers;

import augusto108.ces.appointmenttracker.controllers.PhysicianController;
import augusto108.ces.appointmenttracker.model.Physician;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PhysicianEntityModelAssembler implements RepresentationModelAssembler<Physician, EntityModel<Physician>> {
    @Override
    public EntityModel<Physician> toModel(Physician entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(PhysicianController.class).getPhysicianById(entity.getId())).withSelfRel(),
                linkTo(methodOn(PhysicianController.class).getPhysicians(0, 5, Sort.Direction.ASC, "")).withRel("physicians"),
                linkTo(methodOn(PhysicianController.class).searchPhysicians("", 0, 5, Sort.Direction.ASC, "")).withRel("search")
        );
    }
}
