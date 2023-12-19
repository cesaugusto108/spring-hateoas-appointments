package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.model.entities.Appointment;
import augusto108.ces.appointmenttracker.model.enums.Status;
import augusto108.ces.appointmenttracker.services.AppointmentService;
import augusto108.ces.appointmenttracker.services.PatientService;
import augusto108.ces.appointmenttracker.services.PhysicianService;
import augusto108.ces.appointmenttracker.util.VersioningConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.Filter;
import java.util.LinkedHashMap;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("sec")
@Transactional
class AppointmentControllerTest extends AuthorizeAdminUser {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PhysicianService physicianService;

    @Autowired
    private AppointmentService appointmentService;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();

        final String query1 = "INSERT INTO `tb_patient` (`id`, `first_name`, `last_name`, `email`)\n" +
                "    VALUES (1, 'Pedro', 'Cardoso', 'pedro@email.com');";

        final String query2 = "INSERT INTO `tb_physician` (`id`, `first_name`, `last_name`, `specialty`)\n" +
                "    VALUES (2, 'João', 'Cavalcante', 'DERMATOLOGIST');";

        final String query3 = "INSERT INTO `tb_appointment` (`id`, `patient_id`, `physician_id`, `status`)\n" +
                "    VALUES (1, 1, 2, 'PAYMENT_PENDING');";

        entityManager.createNativeQuery(query1).executeUpdate();
        entityManager.createNativeQuery(query2).executeUpdate();
        entityManager.createNativeQuery(query3).executeUpdate();
    }

    @Test
    void getAppointments() throws Exception {
        final String selfLink = "http://localhost" + VersioningConstant.VERSION + "/appointments?page=0&size=20&sort=id,asc";

        MvcResult result = mockMvc.perform(get(VersioningConstant.VERSION + "/appointments")
                        .with(makeAuthorizedAdminUser())
                        .param("page", "0")
                        .param("size", "20")
                        .param("direction", "ASC")
                        .param("field", "id"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.page.size", is(20)))
                .andExpect(jsonPath("$.page.totalElements", is(1)))
                .andExpect(jsonPath("$.page.totalPages", is(1)))
                .andExpect(jsonPath("$.page.number", is(0)))
                .andExpect(jsonPath("$._links.self.href", is(selfLink)))
                .andReturn();

        final String content = result.getResponse().getContentAsString();
        final int pageJsonKeySetSize = objectMapper.readValue(content, LinkedHashMap.class).keySet().size();

        assertEquals(3, pageJsonKeySetSize);
    }

    @Test
    void searchAppointments() throws Exception {
        final String selfLink = "http://localhost" + VersioningConstant.VERSION + "/appointments/search?page=0&size=20&sort=id,asc";

        MvcResult result = mockMvc.perform(get(VersioningConstant.VERSION + "/appointments/search")
                        .with(makeAuthorizedAdminUser())
                        .param("search", "pending")
                        .param("page", "0")
                        .param("size", "20")
                        .param("direction", "ASC")
                        .param("field", "id"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.page.totalElements", is(1)))
                .andExpect(jsonPath("$._embedded.appointmentList", hasSize(1)))
                .andExpect(jsonPath("$._links.self.href", is(selfLink)))
                .andReturn();

        final String content = result.getResponse().getContentAsString();
        final int pageJsonKeySetSize = objectMapper.readValue(content, LinkedHashMap.class).keySet().size();

        assertEquals(3, pageJsonKeySetSize);
    }

    @Test
    void getAppointmentById() throws Exception {
        mockMvc.perform(get(VersioningConstant.VERSION + "/appointments/{id}", 1)
                        .with(makeAuthorizedAdminUser()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.patient.email", is("pedro@email.com")))
                .andExpect(jsonPath("$.physician.specialty", is("DERMATOLOGIST")))
                .andExpect(jsonPath("$.status", is("PAYMENT_PENDING")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost" + VersioningConstant.VERSION + "/appointments/1")))
                .andExpect(jsonPath("$._links.confirm.href", is("http://localhost" + VersioningConstant.VERSION + "/appointments/1/confirm")))
                .andExpect(jsonPath("$._links.cancel.href", is("http://localhost" + VersioningConstant.VERSION + "/appointments/1/cancel")))
                .andExpect(jsonPath("$._links.appointments.href", is(VersioningConstant.VERSION + "/appointments/?page=0&size=5&direction=ASC&field=id")))
                .andReturn();
    }

    @Test
    void saveAppointment() throws Exception {
        final Appointment appointment = new Appointment();
        appointment.setPatient(patientService.getPatient(1L));
        appointment.setPhysician(physicianService.getPhysician(2L));

        mockMvc.perform(post(VersioningConstant.VERSION + "/appointments")
                        .with(makeAuthorizedAdminUser())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointment)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.patient.email", is("pedro@email.com")))
                .andExpect(jsonPath("$.physician.specialty", is("DERMATOLOGIST")))
                .andExpect(jsonPath("$.status", is("PAYMENT_PENDING")));
    }

    @Test
    void confirmAppointment() throws Exception {
        mockMvc.perform(patch(VersioningConstant.VERSION + "/appointments/1/confirm")
                        .with(makeAuthorizedAdminUser()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.status", is("CONFIRMED")));

        mockMvc.perform(patch(VersioningConstant.VERSION + "/appointments/1/confirm")
                        .with(makeAuthorizedAdminUser()))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.status", is("METHOD_NOT_ALLOWED")))
                .andExpect(jsonPath("$.statusCode", is(405)))
                .andExpect(jsonPath("$.message", is("Cannot confirm an appointment with current status: CONFIRMED")));
    }

    @Test
    void finishAppointment() throws Exception {
        Appointment appointment = appointmentService.getAppointment(1L);
        appointment.setStatus(Status.CONFIRMED);

        mockMvc.perform(patch(VersioningConstant.VERSION + "/appointments/1/finish")
                        .with(makeAuthorizedAdminUser()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.status", is("FINISHED")));

        mockMvc.perform(patch(VersioningConstant.VERSION + "/appointments/1/finish")
                        .with(makeAuthorizedAdminUser()))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.status", is("METHOD_NOT_ALLOWED")))
                .andExpect(jsonPath("$.statusCode", is(405)))
                .andExpect(jsonPath("$.message", is("Cannot finish an appointment with current status: FINISHED")));
    }

    @Test
    void cancelAppointment() throws Exception {
        mockMvc.perform(patch(VersioningConstant.VERSION + "/appointments/1/cancel")
                        .with(makeAuthorizedAdminUser()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.status", is("CANCELLED")));

        mockMvc.perform(patch(VersioningConstant.VERSION + "/appointments/1/cancel")
                        .with(makeAuthorizedAdminUser()))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.status", is("METHOD_NOT_ALLOWED")))
                .andExpect(jsonPath("$.statusCode", is(405)))
                .andExpect(jsonPath("$.message", is("Cannot cancel an appointment with current status: CANCELLED")));
    }
}