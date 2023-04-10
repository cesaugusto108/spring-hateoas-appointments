package augusto108.ces.appointmenttracker.assemblers;

import augusto108.ces.appointmenttracker.controllers.PatientController;
import augusto108.ces.appointmenttracker.model.Patient;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PatientEntityModelAssembler implements RepresentationModelAssembler<Patient, EntityModel<Patient>> {
    @Override
    public EntityModel<Patient> toModel(Patient entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(PatientController.class).getPatientById(entity.getId())).withSelfRel(),
                linkTo(methodOn(PatientController.class).getPatients(0, 5, Sort.Direction.ASC, "")).withRel("patients"),
                linkTo(methodOn(PatientController.class).searchPatients("", 0, 5, Sort.Direction.ASC, "")).withRel("search")
        );
    }
}
