package augusto108.ces.appointmenttracker.converters;

import augusto108.ces.appointmenttracker.controllers.PhysicianController;
import augusto108.ces.appointmenttracker.model.entities.Physician;
import augusto108.ces.appointmenttracker.model.representations.PhysicianModel;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PhysicianModelConverter extends RepresentationModelAssemblerSupport<Physician, PhysicianModel>
{

	public PhysicianModelConverter()
	{
		super(PhysicianController.class, PhysicianModel.class);
	}

	@Override
	public PhysicianModel toModel(Physician entity)
	{
		final PhysicianModel physicianModel = new PhysicianModel();
		BeanUtils.copyProperties(entity, physicianModel);
		return physicianModel;
	}
}
