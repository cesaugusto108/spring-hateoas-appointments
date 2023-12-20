package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.assemblers.PhysicianEntityModelAssembler;
import augusto108.ces.appointmenttracker.converters.PhysicianModelConverter;
import augusto108.ces.appointmenttracker.helpers.DefaultParameterObj;
import augusto108.ces.appointmenttracker.model.entities.Physician;
import augusto108.ces.appointmenttracker.model.representations.PhysicianModel;
import augusto108.ces.appointmenttracker.services.PhysicianService;
import augusto108.ces.appointmenttracker.util.VersioningConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Physicians controller", description = "Managing physicians")
@RestController
@RequestMapping(VersioningConstant.VERSION + "/physicians")
@RequiredArgsConstructor
public class PhysicianController {

    private final PhysicianService service;
    private final PhysicianModelConverter converter;
    private final PagedResourcesAssembler<Physician> resourcesAssembler;
    private final PhysicianEntityModelAssembler modelAssembler;

    private final Class<PhysicianController> controller = PhysicianController.class;
    private final DefaultParameterObj param = new DefaultParameterObj();

    private final Link aggregateRoot =
            linkTo(methodOn(controller)
                    .getPhysicians(param.getPage(), param.getSize(), param.getDirection(), param.getField()))
                    .withRel("physicians");

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get physicians")
    @GetMapping(value = "", produces = "application/hal+json")
    public ResponseEntity<PagedModel<EntityModel<Physician>>> getPhysicians(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(defaultValue = "id") String field
    ) {
        final Page<Physician> physicians = service.findAll(page, size, direction, field);
        final PagedModel<EntityModel<Physician>> pagedModel = resourcesAssembler.toModel(physicians, modelAssembler);
        return ResponseEntity.status(200).body(pagedModel);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "search physicians")
    @GetMapping(value = "/search", produces = "application/hal+json")
    public ResponseEntity<PagedModel<EntityModel<Physician>>> searchPhysicians(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(defaultValue = "id") String field
    ) {
        final Page<Physician> physicians = service.findPhysicianByNameLikeOrSpecialtyLike(search, page, size, direction, field);
        final PagedModel<EntityModel<Physician>> pagedModel = resourcesAssembler.toModel(physicians, modelAssembler);
        return ResponseEntity.status(200).body(pagedModel);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get physician by id")
    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity<PhysicianModel> getPhysicianById(@PathVariable("id") Long id) {
        final Link self = linkTo(methodOn(controller).getPhysicianById(id)).withSelfRel();
        final Physician physician = service.getPhysician(id);
        final PhysicianModel physicianModel = converter.toModel(physician);
        physicianModel.add(self, aggregateRoot);
        return ResponseEntity.status(200).body(physicianModel);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "save physician")
    @PostMapping(value = "", produces = "application/hal+json", consumes = "application/json")
    public ResponseEntity<PhysicianModel> savePhysician(@RequestBody Physician physician) {
        final Physician savedPhysician = service.savePhysician(physician);
        final Link self = linkTo(methodOn(controller).getPhysicianById(savedPhysician.getId())).withSelfRel();
        final PhysicianModel physicianModel = converter.toModel(savedPhysician);
        physicianModel.add(self, aggregateRoot);
        return ResponseEntity.status(201).body(physicianModel);
    }
}
