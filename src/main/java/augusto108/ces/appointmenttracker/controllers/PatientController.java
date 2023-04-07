package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.converters.PatientModelConverter;
import augusto108.ces.appointmenttracker.model.Patient;
import augusto108.ces.appointmenttracker.model.PatientModel;
import augusto108.ces.appointmenttracker.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService service;
    private final PatientModelConverter converter;
    private final PagedResourcesAssembler<Patient> resourcesAssembler;

    @GetMapping(value = "", produces = "application/hal+json")
    public ResponseEntity<PagedModel<PatientModel>> getPatients(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(resourcesAssembler.toModel(service.getPatients(page, size), converter));
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity<PatientModel> getPatient(@PathVariable("id") Long id) {
        Class<PatientController> controller = PatientController.class;
        int defaultPage = 0;
        int defaultPageSize = 5;

        Link self = linkTo(methodOn(controller).getPatient(id)).withSelfRel();
        Link modelsLink = linkTo(methodOn(controller).getPatients(defaultPage, defaultPageSize)).withRel("patients");

        return ResponseEntity.ok(converter.toModel(service.getPatient(id)).add(Arrays.asList(self, modelsLink)));
    }
}
