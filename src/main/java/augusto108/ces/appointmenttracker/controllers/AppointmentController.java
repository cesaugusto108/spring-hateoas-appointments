package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.converters.AppointmentModelConverter;
import augusto108.ces.appointmenttracker.model.Appointment;
import augusto108.ces.appointmenttracker.model.AppointmentModel;
import augusto108.ces.appointmenttracker.model.Patient;
import augusto108.ces.appointmenttracker.model.Physician;
import augusto108.ces.appointmenttracker.model.enums.Status;
import augusto108.ces.appointmenttracker.services.AppointmentService;
import augusto108.ces.appointmenttracker.services.PatientService;
import augusto108.ces.appointmenttracker.services.PhysicianService;
import lombok.Getter;
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
    private final PatientService patientService;
    private final PhysicianService physicianService;
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
        Link confirmLink = linkTo(methodOn(controller).confirmAppointment(id)).withRel("confirm");

        Appointment a = service.getAppointment(id);

        if (a.getStatus() == Status.PAYMENT_PENDING)
            return ResponseEntity.ok(converter.toModel(a).add(self, confirmLink, aggregateRoot));

        return ResponseEntity.ok(converter.toModel(a).add(self, aggregateRoot));
    }

    @PostMapping(value = "", produces = "application/hal+json", consumes = "application/json")
    public ResponseEntity<AppointmentModel> saveAppointment(@RequestBody Appointment appointment) {
        appointment.setStatus(Status.PAYMENT_PENDING);
        final Appointment a = service.saveAppointment(appointment);

        Patient patient = patientService.getPatient(a.getPatient().getId());
        patient.getAppointments().add(a);
        patientService.savePatient(patient);

        Physician physician = physicianService.getPhysician(a.getPhysician().getId());
        physician.getAppointments().add(a);
        physicianService.savePhysician(physician);

        Link self = linkTo(methodOn(controller).getAppointmentById(a.getId())).withSelfRel();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(converter.toModel(a).add(self, aggregateRoot));
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<?> confirmAppointment(@PathVariable("id") Long id) {
        Appointment a = service.getAppointment(id);

        if (a.getStatus() == Status.PAYMENT_PENDING) {
            a.setStatus(Status.CONFIRMED);
            Link self = linkTo(methodOn(controller).getAppointmentById(a.getId())).withSelfRel();

            return ResponseEntity.ok(converter.toModel(service.saveAppointment(a)).add(self, aggregateRoot));
        }

        final NotAllowedResponse response = new NotAllowedResponse("Cannot confirm an appointment with current status: " + a.getStatus());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @Getter
    private static class NotAllowedResponse {
        private final String message;
        private final HttpStatus status;
        private final long statusCode;

        public NotAllowedResponse(String message) {
            this.message = message;
            this.status = HttpStatus.METHOD_NOT_ALLOWED;
            this.statusCode = status.value();
        }
    }
}

