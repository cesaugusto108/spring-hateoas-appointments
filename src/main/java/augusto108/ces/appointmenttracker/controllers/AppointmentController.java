package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.converters.AppointmentModelConverter;
import augusto108.ces.appointmenttracker.model.Appointment;
import augusto108.ces.appointmenttracker.model.AppointmentModel;
import augusto108.ces.appointmenttracker.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService service;
    private final AppointmentModelConverter converter;
    private final PagedResourcesAssembler<Appointment> pagedResourcesAssembler;

    @GetMapping(value = "", produces = "application/hal+json")
    public ResponseEntity<PagedModel<AppointmentModel>> getAppointments(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(service.getAppointments(page, size), converter));
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity<AppointmentModel> getAppointmentById(@PathVariable("id") Long id) {
        Class<AppointmentController> controller = AppointmentController.class;
        int defaultPage = 0;
        int defaultPageSize = 5;

        Link self = linkTo(methodOn(controller).getAppointmentById(id)).withSelfRel();
        Link modelsLink = linkTo(methodOn(controller).getAppointments(defaultPage, defaultPageSize)).withRel("appointments");

        return ResponseEntity.ok(converter.toModel(service.getAppointment(id)).add(Arrays.asList(self, modelsLink)));
    }
}

