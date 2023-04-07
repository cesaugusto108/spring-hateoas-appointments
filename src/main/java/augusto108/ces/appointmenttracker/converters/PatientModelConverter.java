package augusto108.ces.appointmenttracker.converters;

import augusto108.ces.appointmenttracker.controllers.PatientController;
import augusto108.ces.appointmenttracker.model.Patient;
import augusto108.ces.appointmenttracker.model.PatientModel;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PatientModelConverter extends RepresentationModelAssemblerSupport<Patient, PatientModel> {
    public PatientModelConverter() {
        super(PatientController.class, PatientModel.class);
    }

    @Override
    public PatientModel toModel(Patient entity) {
        PatientModel patientModel = new PatientModel();

        BeanUtils.copyProperties(entity, patientModel);

        return patientModel;
    }
}
