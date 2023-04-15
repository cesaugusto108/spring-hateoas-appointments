package augusto108.ces.appointmenttracker.controllers;

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

@RestController
@RequestMapping("/")
public class AppController {
    @GetMapping(produces = "application/hal+json")
    public ResponseEntity<List<Link>> appIndex() {
        Link self = linkTo(methodOn(AppController.class).appIndex()).withSelfRel();
        Link appointments = linkTo(methodOn(AppointmentController.class).getAppointments(0, 5, Sort.Direction.ASC, "id")).withRel("appointments");
        Link patients = linkTo(methodOn(PatientController.class).getPatients(0, 5, Sort.Direction.ASC, "id")).withRel("patients");
        Link physicians = linkTo(methodOn(PhysicianController.class).getPhysicians(0, 5, Sort.Direction.ASC, "id")).withRel("physicians");

        return ResponseEntity.ok(Arrays.asList(self, appointments, patients, physicians));
    }
}
