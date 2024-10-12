package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.assemblers.PatientEntityModelAssembler;
import augusto108.ces.appointmenttracker.converters.PatientModelConverter;
import augusto108.ces.appointmenttracker.helpers.DefaultParameterObj;
import augusto108.ces.appointmenttracker.model.entities.Patient;
import augusto108.ces.appointmenttracker.model.representations.PatientModel;
import augusto108.ces.appointmenttracker.services.PatientService;
import augusto108.ces.appointmenttracker.util.VersioningConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Patients controller", description = "Managing patients")
@RestController
@RequestMapping(VersioningConstant.VERSION + "/patients")
@RequiredArgsConstructor
public class PatientController
{

	private final PatientService service;
	private final PatientModelConverter converter;
	private final PagedResourcesAssembler<Patient> resourcesAssembler;
	private final PatientEntityModelAssembler modelAssembler;

	private final Class<PatientController> controller = PatientController.class;
	private final DefaultParameterObj param = new DefaultParameterObj();

	private final Link aggregateRoot =
		linkTo(methodOn(controller)
			.getPatients(param.getPage(), param.getSize(), param.getDirection(), param.getField()))
			.withRel("patients");

	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "get patients")
	@GetMapping(value = "", produces = "application/hal+json")
	public ResponseEntity<PagedModel<EntityModel<Patient>>> getPatients(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "5") int size,
		@RequestParam(defaultValue = "ASC") Sort.Direction direction,
		@RequestParam(defaultValue = "id") String field
	)
	{
		final Page<Patient> patients = service.findAll(page, size, direction, field);
		final PagedModel<EntityModel<Patient>> pagedModel = resourcesAssembler.toModel(patients, modelAssembler);
		return ResponseEntity.status(200).body(pagedModel);
	}

	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "search patients")
	@GetMapping(value = "/search", produces = "application/hal+json")
	public ResponseEntity<PagedModel<EntityModel<Patient>>> searchPatients(
		@RequestParam(defaultValue = "") String search,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "5") int size,
		@RequestParam(defaultValue = "ASC") Sort.Direction direction,
		@RequestParam(defaultValue = "id") String field
	)
	{
		final Page<Patient> patients = service.findPatientByNameLikeOrEmailLike(search, page, size, direction, field);
		final PagedModel<EntityModel<Patient>> pagedModel = resourcesAssembler.toModel(patients, modelAssembler);
		return ResponseEntity.status(200).body(pagedModel);
	}

	@ResponseStatus(HttpStatus.OK)
	@Operation(summary = "get patient by id")
	@GetMapping(value = "/{id}", produces = "application/hal+json")
	public ResponseEntity<PatientModel> getPatientById(@PathVariable("id") Long id)
	{
		final Link self = linkTo(methodOn(controller).getPatientById(id)).withSelfRel();
		final Patient patient = service.getPatient(id);
		final PatientModel patientModel = converter.toModel(patient);
		patientModel.add(self, aggregateRoot);
		return ResponseEntity.status(200).body(patientModel);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "save patient")
	@PostMapping(value = "", produces = "application/hal+json", consumes = "application/json")
	public ResponseEntity<PatientModel> savePatient(@RequestBody Patient patient)
	{
		final Patient savedPatient = service.savePatient(patient);
		final Link self = linkTo(methodOn(controller).getPatientById(savedPatient.getId())).withSelfRel();
		final PatientModel patientModel = converter.toModel(savedPatient);
		patientModel.add(self, aggregateRoot);
		final URI uri = patientModel.getRequiredLink(IanaLinkRelations.SELF).toUri();
		return ResponseEntity.status(201).location(uri).body(patientModel);
	}
}
