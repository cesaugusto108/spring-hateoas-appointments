package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.model.entities.Physician;
import augusto108.ces.appointmenttracker.model.enums.Specialty;
import augusto108.ces.appointmenttracker.services.EmployeeService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("sec")
@Transactional
class PhysicianControllerTest extends AuthorizeAdminUser {

    private MockMvc mockMvc;

    private final WebApplicationContext context;
    private final ObjectMapper objectMapper;
    private final Filter springSecurityFilterChain;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    PhysicianControllerTest(WebApplicationContext context,
                            ObjectMapper objectMapper,
                            Filter springSecurityFilterChain,
                            EmployeeService employeeService) {
        super(employeeService);
        this.context = context;
        this.objectMapper = objectMapper;
        this.springSecurityFilterChain = springSecurityFilterChain;
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();

        final String query = "INSERT INTO `tb_physician` (`id`, `first_name`, `last_name`, `specialty`)\n" +
                "    VALUES (1, 'Marcela', 'Cavalcante', 'GENERAL_PRACTITIONER');";

        entityManager.createNativeQuery(query).executeUpdate();
    }

    @AfterEach
    void tearDown() {
        entityManager.createNativeQuery("delete from `tb_physician`;");
    }

    @Test
    void getPhysicians() throws Exception {
        final String selfLink = "http://localhost" + VersioningConstant.VERSION + "/physicians?page=0&size=20&sort=id,asc";

        MvcResult result = mockMvc.perform(get(VersioningConstant.VERSION + "/physicians").with(makeAuthorizedAdminUser())
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
    void searchPhysicians() throws Exception {
        final String selfLink = "http://localhost" + VersioningConstant.VERSION + "/physicians/search?page=0&size=20&sort=id,asc";

        MvcResult result = mockMvc.perform(get(VersioningConstant.VERSION + "/physicians/search").with(makeAuthorizedAdminUser())
                        .param("search", "Marcela")
                        .param("page", "0")
                        .param("size", "20")
                        .param("direction", "ASC")
                        .param("field", "id"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.page.totalElements", is(1)))
                .andExpect(jsonPath("$._embedded.physicianList", hasSize(1)))
                .andExpect(jsonPath("$._links.self.href", is(selfLink)))
                .andReturn();

        final String content = result.getResponse().getContentAsString();
        final int pageJsonKeySetSize = objectMapper.readValue(content, LinkedHashMap.class).keySet().size();
        assertEquals(3, pageJsonKeySetSize);
    }

    @Test
    void getPhysicianById() throws Exception {
        mockMvc.perform(get(VersioningConstant.VERSION + "/physicians/{id}", 1).with(makeAuthorizedAdminUser()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.firstName", is("Marcela")))
                .andExpect(jsonPath("$.lastName", is("Cavalcante")))
                .andExpect(jsonPath("$.specialty", is("GENERAL_PRACTITIONER")));
    }

    @Test
    void savePhysician() throws Exception {
        final Physician physician = new Physician();
        physician.setFirstName("Nelson");
        physician.setLastName("Muntz");
        physician.setSpecialty(Specialty.ENDOCRINOLOGIST);

        final MvcResult result = mockMvc.perform(post(VersioningConstant.VERSION + "/physicians").with(makeAuthorizedAdminUser())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(physician)))
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Nelson")))
                .andExpect(jsonPath("$.lastName", is("Muntz")))
                .andExpect(jsonPath("$.specialty", is("ENDOCRINOLOGIST")))
                .andReturn();

        final Physician savedPhysician = objectMapper.readerFor(Physician.class).readValue(result.getResponse().getContentAsString());
        final String locationHeader = result.getResponse().getHeader("Location");
        final String uri = "http://localhost" + VersioningConstant.VERSION + "/physicians/" + savedPhysician.getId();
        assertEquals(uri, locationHeader);
    }
}