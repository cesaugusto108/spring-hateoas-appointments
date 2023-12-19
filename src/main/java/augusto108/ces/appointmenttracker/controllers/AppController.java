package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.helpers.DefaultParameterObj;
import augusto108.ces.appointmenttracker.util.VersioningConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Aggregate root links", description = "Links to appointments, patients and physicians")
@RestController
@RequestMapping(VersioningConstant.VERSION + "/")
@RequiredArgsConstructor
public class AppController {
    private final DefaultParameterObj param = new DefaultParameterObj();

    @Operation(summary = "App root links")
    @GetMapping(produces = "application/hal+json")
    public ResponseEntity<List<Link>> appIndex() {
        final int page = param.getPage();
        final int size = param.getSize();
        final Sort.Direction direction = param.getDirection();
        final String field = param.getField();

        final Link self = linkTo(methodOn(AppController.class).appIndex()).withSelfRel();

        final Link appointments = linkTo(methodOn(AppointmentController.class)
                .getAppointments(page, size, direction, field))
                .withRel("appointments");

        final Link patients = linkTo(methodOn(PatientController.class)
                .getPatients(page, size, direction, field))
                .withRel("patients");

        final Link physicians = linkTo(methodOn(PhysicianController.class)
                .getPhysicians(page, size, direction, field))
                .withRel("physicians");

        return ResponseEntity.ok(Arrays.asList(self, appointments, patients, physicians));
    }
}
