package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.assemblers.AppointmentEntityModelAssembler;
import augusto108.ces.appointmenttracker.converters.AppointmentModelConverter;
import augusto108.ces.appointmenttracker.helpers.DefaultParameterObj;
import augusto108.ces.appointmenttracker.model.entities.Appointment;
import augusto108.ces.appointmenttracker.model.entities.Patient;
import augusto108.ces.appointmenttracker.model.entities.Physician;
import augusto108.ces.appointmenttracker.model.enums.Status;
import augusto108.ces.appointmenttracker.model.representations.AppointmentModel;
import augusto108.ces.appointmenttracker.services.AppointmentService;
import augusto108.ces.appointmenttracker.services.PatientService;
import augusto108.ces.appointmenttracker.services.PhysicianService;
import augusto108.ces.appointmenttracker.util.VersioningConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Appointments controller", description = "Managing appointments")
@RestController
@RequestMapping(VersioningConstant.VERSION + "/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;
    private final PatientService patientService;
    private final PhysicianService physicianService;
    private final AppointmentModelConverter converter;
    private final PagedResourcesAssembler<Appointment> resourcesAssembler;
    private final AppointmentEntityModelAssembler modelAssembler;

    private final Class<AppointmentController> controller = AppointmentController.class;
    private final DefaultParameterObj param = new DefaultParameterObj();

    private final Link aggregateRoot = linkTo(methodOn(controller)
            .getAppointments(param.getPage(), param.getSize(), param.getDirection(), param.getField()))
            .withRel("appointments");

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get appointments")
    @GetMapping(value = "", produces = "application/hal+json")
    public ResponseEntity<PagedModel<EntityModel<Appointment>>> getAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(defaultValue = "id") String field
    ) {
        final Page<Appointment> appointments = service.findAll(page, size, direction, field);
        final PagedModel<EntityModel<Appointment>> pagedModel = resourcesAssembler.toModel(appointments, modelAssembler);
        return ResponseEntity.status(200).body(pagedModel);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "search appointments")
    @GetMapping(value = "/search", produces = "application/hal+json")
    public ResponseEntity<PagedModel<EntityModel<Appointment>>> searchAppointments(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(defaultValue = "id") String field
    ) {
        final Page<Appointment> appointments = service.findAppointmentByStatusOrPersonName(search, page, size, direction, field);
        final PagedModel<EntityModel<Appointment>> pagedModel = resourcesAssembler.toModel(appointments, modelAssembler);
        return ResponseEntity.status(200).body(pagedModel);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get appointment by id")
    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity<AppointmentModel> getAppointmentById(@PathVariable("id") Long id) {
        final Link self = linkTo(methodOn(controller).getAppointmentById(id)).withSelfRel();
        final Link confirmLink = linkTo(methodOn(controller).confirmAppointment(id)).withRel("confirm");
        final Link finishLink = linkTo(methodOn(controller).finishAppointment(id)).withRel("finish");
        final Link cancelLink = linkTo(methodOn(controller).cancelAppointment(id)).withRel("cancel");
        final Appointment appointment = service.getAppointment(id);
        final AppointmentModel appointmentModel = converter.toModel(appointment);
        appointmentModel.add(self, aggregateRoot);

        if (appointment.getStatus() == Status.PAYMENT_PENDING)
            return ResponseEntity.status(200).body(appointmentModel.add(confirmLink, cancelLink));

        if (appointment.getStatus() == Status.CONFIRMED)
            return ResponseEntity.status(200).body(appointmentModel.add(finishLink, cancelLink));

        return ResponseEntity.status(200).body(appointmentModel);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "save appointment")
    @PostMapping(value = "", produces = "application/hal+json", consumes = "application/json")
    public ResponseEntity<AppointmentModel> saveAppointment(@RequestBody Appointment appointment) {
        appointment.setStatus(Status.PAYMENT_PENDING);
        final Appointment savedAppointment = service.saveAppointment(appointment);

        final Patient patient = patientService.getPatient(savedAppointment.getPatient().getId());
        patient.getAppointments().add(savedAppointment);
        patientService.savePatient(patient);

        final Physician physician = physicianService.getPhysician(savedAppointment.getPhysician().getId());
        physician.getAppointments().add(savedAppointment);
        physicianService.savePhysician(physician);

        final Link self = linkTo(methodOn(controller).getAppointmentById(savedAppointment.getId())).withSelfRel();
        final Link confirmLink = linkTo(methodOn(controller).confirmAppointment(savedAppointment.getId())).withRel("confirm");
        final Link cancelLink = linkTo(methodOn(controller).cancelAppointment(savedAppointment.getId())).withRel("cancel");
        final AppointmentModel appointmentModel = converter.toModel(savedAppointment);
        appointmentModel.add(self, confirmLink, cancelLink, aggregateRoot);
        final URI uri = appointmentModel.getRequiredLink(IanaLinkRelations.SELF).toUri();
        return ResponseEntity.status(201).location(uri).body(appointmentModel);
    }

    @Operation(summary = "confirm appointment")
    @PatchMapping(value = "/{id}/confirm", produces = "application/hal+json")
    public ResponseEntity<?> confirmAppointment(@PathVariable("id") Long id) {
        Appointment appointment = service.getAppointment(id);

        if (appointment.getStatus() == Status.PAYMENT_PENDING) {
            appointment.setStatus(Status.CONFIRMED);
            final Link self = linkTo(methodOn(controller).getAppointmentById(appointment.getId())).withSelfRel();
            final Link finishLink = linkTo(methodOn(controller).finishAppointment(id)).withRel("finish");
            final Link cancelLink = linkTo(methodOn(controller).cancelAppointment(id)).withRel("cancel");
            final Appointment confirmedAppointment = service.saveAppointment(appointment);
            final AppointmentModel appointmentModel = converter.toModel(confirmedAppointment);
            appointmentModel.add(self, finishLink, cancelLink, aggregateRoot);
            return ResponseEntity.status(HttpStatus.OK).body(appointmentModel);
        }

        final NotAllowedResponse response =
                new NotAllowedResponse("Cannot confirm an appointment with current status: " + appointment.getStatus());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @Operation(summary = "finish appointment")
    @PatchMapping(value = "/{id}/finish", produces = "application/hal+json")
    public ResponseEntity<?> finishAppointment(@PathVariable("id") Long id) {
        Appointment appointment = service.getAppointment(id);

        if (appointment.getStatus() == Status.CONFIRMED) {
            appointment.setStatus(Status.FINISHED);
            final Link self = linkTo(methodOn(controller).getAppointmentById(appointment.getId())).withSelfRel();
            final AppointmentModel appointmentModel = converter.toModel(appointment);
            appointmentModel.add(self, aggregateRoot);
            return ResponseEntity.status(HttpStatus.OK).body(appointmentModel);
        }

        final NotAllowedResponse response =
                new NotAllowedResponse("Cannot finish an appointment with current status: " + appointment.getStatus());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @Operation(summary = "cancel appointment")
    @PatchMapping(value = "/{id}/cancel", produces = "application/hal+json")
    public ResponseEntity<?> cancelAppointment(@PathVariable("id") Long id) {
        Appointment appointment = service.getAppointment(id);

        if (appointment.getStatus() == Status.PAYMENT_PENDING || appointment.getStatus() == Status.CONFIRMED) {
            appointment.setStatus(Status.CANCELLED);
            final Link self = linkTo(methodOn(controller).getAppointmentById(appointment.getId())).withSelfRel();
            final AppointmentModel appointmentModel = converter.toModel(appointment);
            appointmentModel.add(self, aggregateRoot);
            return ResponseEntity.status(HttpStatus.OK).body(appointmentModel);
        }

        final NotAllowedResponse response =
                new NotAllowedResponse("Cannot cancel an appointment with current status: " + appointment.getStatus());

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

