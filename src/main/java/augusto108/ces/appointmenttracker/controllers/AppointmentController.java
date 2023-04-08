package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.converters.AppointmentModelConverter;
import augusto108.ces.appointmenttracker.model.Appointment;
import augusto108.ces.appointmenttracker.model.AppointmentModel;
import augusto108.ces.appointmenttracker.model.enums.Status;
import augusto108.ces.appointmenttracker.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService service;
    private final AppointmentModelConverter converter;
    private final PagedResourcesAssembler<Appointment> pagedResourcesAssembler;

    private final Class<AppointmentController> controller = AppointmentController.class;
    private final int defaultPage = 0;
    private final int defaultPageSize = 5;
    private final Link aggregateRoot = linkTo(methodOn(controller).getAppointments(defaultPage, defaultPageSize)).withRel("appointments");

    @GetMapping(value = "", produces = "application/hal+json")
    public ResponseEntity<PagedModel<AppointmentModel>> getAppointments(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(service.getAppointments(page, size), converter));
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity<AppointmentModel> getAppointmentById(@PathVariable("id") Long id) {
        Link self = linkTo(methodOn(controller).getAppointmentById(id)).withSelfRel();

        return ResponseEntity.ok(converter.toModel(service.getAppointment(id)).add(self, aggregateRoot));
    }

    @PostMapping(value = "", produces = "application/hal+json", consumes = "application/json")
    public ResponseEntity<AppointmentModel> saveAppointment(@RequestBody Appointment appointment) {
        appointment.setStatus(Status.PAYMENT_PENDING);
        final Appointment a = service.saveAppointment(appointment);
        Link self = linkTo(methodOn(controller).getAppointmentById(a.getId())).withSelfRel();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(converter.toModel(a).add(self, aggregateRoot));
    }
}

