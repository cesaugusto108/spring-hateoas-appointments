package augusto108.ces.appointmenttracker.converters;

import augusto108.ces.appointmenttracker.controllers.AppointmentController;
import augusto108.ces.appointmenttracker.model.entities.Appointment;
import augusto108.ces.appointmenttracker.model.representations.AppointmentModel;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class AppointmentModelConverter extends RepresentationModelAssemblerSupport<Appointment, AppointmentModel>
{

	public AppointmentModelConverter()
	{
		super(AppointmentController.class, AppointmentModel.class);
	}

	@Override
	public AppointmentModel toModel(Appointment appointment)
	{
		final AppointmentModel appointmentModel = new AppointmentModel();
		BeanUtils.copyProperties(appointment, appointmentModel);
		return appointmentModel;
	}
}
