package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.model.Patient;
import augusto108.ces.appointmenttracker.util.VersioningConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
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

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("sec")
@Transactional
class PatientControllerTest extends AuthorizeAdminUser {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Filter springSecurityFilterChain;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();

        final String query = "INSERT INTO `tb_patient` (`id`, `first_name`, `last_name`, `email`)\n" +
                "    VALUES (1, 'Pedro', 'Cardoso', 'pedro@email.com');";

        entityManager.createNativeQuery(query).executeUpdate();
    }

    @AfterEach
    void tearDown() {
        entityManager.createNativeQuery("delete from `tb_patient`;");
    }

    @Test
    void getPatients() throws Exception {
        final String selfLink = "http://localhost" + VersioningConstant.VERSION + "/patients?page=0&size=20&sort=id,asc";

        MvcResult result = mockMvc.perform(get(VersioningConstant.VERSION + "/patients").with(makeAuthorizedAdminUser())
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
    void searchPatients() throws Exception {
        final String selfLink = "http://localhost" + VersioningConstant.VERSION + "/patients/search?page=0&size=20&sort=id,asc";

        MvcResult result = mockMvc.perform(get(VersioningConstant.VERSION + "/patients/search").with(makeAuthorizedAdminUser())
                        .param("search", "Cardoso")
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
    void getPatientById() throws Exception {
        mockMvc.perform(get(VersioningConstant.VERSION + "/patients/{id}", 1).with(makeAuthorizedAdminUser()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.firstName", is("Pedro")))
                .andExpect(jsonPath("$.lastName", is("Cardoso")))
                .andExpect(jsonPath("$.email", is("pedro@email.com")));
    }

    @Test
    void savePatient() throws Exception {
        final Patient patient = new Patient();
        patient.setFirstName("Leonardo");
        patient.setLastName("Ribeiro");
        patient.setEmail("leonardo@email.com");

        mockMvc.perform(post(VersioningConstant.VERSION + "/patients").with(makeAuthorizedAdminUser())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Leonardo")))
                .andExpect(jsonPath("$.lastName", is("Ribeiro")))
                .andExpect(jsonPath("$.email", is("leonardo@email.com")));
    }
}