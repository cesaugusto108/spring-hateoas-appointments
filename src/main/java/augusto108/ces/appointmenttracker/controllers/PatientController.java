package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.converters.PatientModelConverter;
import augusto108.ces.appointmenttracker.model.Patient;
import augusto108.ces.appointmenttracker.model.PatientModel;
import augusto108.ces.appointmenttracker.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService service;
    private final PatientModelConverter converter;
    private final PagedResourcesAssembler<Patient> resourcesAssembler;

    private final Class<PatientController> controller = PatientController.class;
    private final int defaultPage = 0;
    private final int defaultPageSize = 5;
    private final Link aggregateRoot = linkTo(methodOn(controller).getPatients(defaultPage, defaultPageSize)).withRel("patients");

    @GetMapping(value = "", produces = "application/hal+json")
    public ResponseEntity<PagedModel<PatientModel>> getPatients(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(resourcesAssembler.toModel(service.getPatients(page, size), converter));
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity<PatientModel> getPatientById(@PathVariable("id") Long id) {
        Link self = linkTo(methodOn(controller).getPatientById(id)).withSelfRel();

        return ResponseEntity.ok(converter.toModel(service.getPatient(id)).add(self, aggregateRoot));
    }

    @PostMapping(value = "", produces = "application/hal+json", consumes = "application/json")
    public ResponseEntity<PatientModel> savePatient(@RequestBody Patient patient) {
        final Patient p = service.savePatient(patient);
        Link self = linkTo(methodOn(controller).getPatientById(p.getId())).withSelfRel();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(converter.toModel(p).add(self, aggregateRoot));
    }
}
