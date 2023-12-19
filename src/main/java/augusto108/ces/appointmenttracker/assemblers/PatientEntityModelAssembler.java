package augusto108.ces.appointmenttracker.assemblers;

import augusto108.ces.appointmenttracker.controllers.PatientController;
import augusto108.ces.appointmenttracker.helpers.DefaultParameterObj;
import augusto108.ces.appointmenttracker.model.Patient;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PatientEntityModelAssembler implements RepresentationModelAssembler<Patient, EntityModel<Patient>> {

    private final DefaultParameterObj param = new DefaultParameterObj();

    @Override
    public EntityModel<Patient> toModel(Patient patient) {
        final int page = param.getPage();
        final int size = param.getSize();
        final Sort.Direction direction = param.getDirection();
        final String field = param.getField();
        return EntityModel.of(
                patient,
                linkTo(methodOn(PatientController.class).getPatientById(patient.getId())).withSelfRel(),
                linkTo(methodOn(PatientController.class).getPatients(page, size, direction, field)).withRel("patients"),
                linkTo(methodOn(PatientController.class).searchPatients("", page, size, direction, field)).withRel("search")
        );
    }
}
