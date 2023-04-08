package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.converters.PhysicianModelConverter;
import augusto108.ces.appointmenttracker.model.Physician;
import augusto108.ces.appointmenttracker.model.PhysicianModel;
import augusto108.ces.appointmenttracker.services.PhysicianService;
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
@RequestMapping("/physicians")
@RequiredArgsConstructor
public class PhysicianController {
    private final PhysicianService service;
    private final PhysicianModelConverter converter;
    private final PagedResourcesAssembler<Physician> resourcesAssembler;

    @GetMapping(value = "", produces = "application/hal+json")
    public ResponseEntity<PagedModel<PhysicianModel>> getPhysicians(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(resourcesAssembler.toModel(service.getPhysicians(page, size), converter));
    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity<PhysicianModel> getPhysicianById(@PathVariable("id") Long id) {
        Class<PhysicianController> controller = PhysicianController.class;
        int defaultPage = 0;
        int defaultPageSize = 5;

        Link self = linkTo(methodOn(controller).getPhysicianById(id)).withSelfRel();
        Link modelsLink = linkTo(methodOn(controller).getPhysicians(defaultPage, defaultPageSize)).withRel("physicians");

        return ResponseEntity.ok(converter.toModel(service.getPhysician(id)).add(Arrays.asList(self, modelsLink)));
    }
}
