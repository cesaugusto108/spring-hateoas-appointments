package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.model.Appointment;
import augusto108.ces.appointmenttracker.model.Patient;
import augusto108.ces.appointmenttracker.model.Physician;
import augusto108.ces.appointmenttracker.model.enums.Status;
import augusto108.ces.appointmenttracker.services.PatientService;
import augusto108.ces.appointmenttracker.services.PhysicianService;
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

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    void getAppointments() throws Exception {
        final String selfLink = "http://localhost/appointments?page=0&size=20&sort=id,asc";

        MvcResult result = mockMvc.perform(get("/appointments").with(makeAuthorizedAdminUser())
                        .param("page", "0")
                        .param("size", "20")
                        .param("direction", "ASC")
                        .param("field", "id"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.page.size", is(20)))
                .andExpect(jsonPath("$.page.totalElements", is(20)))
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
        final String selfLink = "http://localhost/appointments/search?page=0&size=20&sort=id,asc";

        MvcResult result = mockMvc.perform(get("/appointments/search").with(makeAuthorizedAdminUser())
                        .param("search", "finished")
                        .param("page", "0")
                        .param("size", "20")
                        .param("direction", "ASC")
                        .param("field", "id"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.page.totalElements", is(5)))
                .andExpect(jsonPath("$._embedded.appointmentList", hasSize(5)))
                .andExpect(jsonPath("$._links.self.href", is(selfLink)))
                .andReturn();

        final String content = result.getResponse().getContentAsString();
        final int pageJsonKeySetSize = objectMapper.readValue(content, LinkedHashMap.class).keySet().size();

        assertEquals(3, pageJsonKeySetSize);
    }

    @Test
    void getAppointmentById() throws Exception {
        mockMvc.perform(get("/appointments/{id}", 1).with(makeAuthorizedAdminUser()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.patient.email", is("pedro@email.com")))
                .andExpect(jsonPath("$.physician.specialty", is("DERMATOLOGIST")))
                .andExpect(jsonPath("$.status", is("PAYMENT_PENDING")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/appointments/1")))
                .andExpect(jsonPath("$._links.confirm.href", is("http://localhost/appointments/1/confirm")))
                .andExpect(jsonPath("$._links.cancel.href", is("http://localhost/appointments/1/cancel")))
                .andExpect(jsonPath("$._links.appointments.href", is("/appointments/?page=0&size=5&direction=ASC&field=id")))
                .andReturn();

        mockMvc.perform(get("/appointments/{id}", 2).with(makeAuthorizedAdminUser()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.patient.email", is("hugo@email.com")))
                .andExpect(jsonPath("$.physician.specialty", is("DERMATOLOGIST")))
                .andExpect(jsonPath("$.status", is("CONFIRMED")))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/appointments/2")))
                .andExpect(jsonPath("$._links.finish.href", is("http://localhost/appointments/2/finish")))
                .andExpect(jsonPath("$._links.cancel.href", is("http://localhost/appointments/2/cancel")))
                .andExpect(jsonPath("$._links.appointments.href", is("/appointments/?page=0&size=5&direction=ASC&field=id")))
                .andReturn();
    }

    @Test
    void saveAppointment() throws Exception {
        final Patient patient = patientService.getPatient(1L);
        final Physician physician = physicianService.getPhysician(3L);
        final Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setPhysician(physician);
        appointment.setStatus(Status.CANCELLED);
        patient.getAppointments().add(appointment);
        physician.getAppointments().add(appointment);

        mockMvc.perform(post("/appointments").with(makeAuthorizedAdminUser())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointment)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.patient.email", is("pedro@email.com")))
                .andExpect(jsonPath("$.physician.specialty", is("CARDIOLOGIST")))
                .andExpect(jsonPath("$.status", is("PAYMENT_PENDING")));
    }

    @Test
    void confirmAppointment() throws Exception {
        mockMvc.perform(patch("/appointments/1/confirm").with(makeAuthorizedAdminUser()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.status", is("CONFIRMED")));

        mockMvc.perform(patch("/appointments/2/confirm").with(makeAuthorizedAdminUser()))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.status", is("METHOD_NOT_ALLOWED")))
                .andExpect(jsonPath("$.statusCode", is(405)))
                .andExpect(jsonPath("$.message", is("Cannot confirm an appointment with current status: CONFIRMED")));
    }

    @Test
    void finishAppointment() throws Exception {
        mockMvc.perform(patch("/appointments/2/finish").with(makeAuthorizedAdminUser()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.status", is("FINISHED")));

        mockMvc.perform(patch("/appointments/3/finish").with(makeAuthorizedAdminUser()))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.status", is("METHOD_NOT_ALLOWED")))
                .andExpect(jsonPath("$.statusCode", is(405)))
                .andExpect(jsonPath("$.message", is("Cannot finish an appointment with current status: FINISHED")));
    }

    @Test
    void cancelAppointment() throws Exception {
        mockMvc.perform(patch("/appointments/2/cancel").with(makeAuthorizedAdminUser()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.status", is("CANCELLED")));

        mockMvc.perform(patch("/appointments/5/cancel").with(makeAuthorizedAdminUser()))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.status", is("METHOD_NOT_ALLOWED")))
                .andExpect(jsonPath("$.statusCode", is(405)))
                .andExpect(jsonPath("$.message", is("Cannot cancel an appointment with current status: CANCELLED")));
    }
}