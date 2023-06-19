package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.model.Physician;
import augusto108.ces.appointmenttracker.model.enums.Specialty;
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
import org.springframework.web.context.WebApplicationContext;

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
class PhysicianControllerTest extends AuthorizeAdminUser {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Filter springSecurityFilterChain;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    void getPhysicians() throws Exception {
        final String selfLink = "http://localhost/physicians?page=0&size=20&sort=id,asc";

        MvcResult result = mockMvc.perform(get("/physicians").with(makeAuthorizedAdminUser())
                        .param("page", "0")
                        .param("size", "20")
                        .param("direction", "ASC")
                        .param("field", "id"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.page.size", is(20)))
                .andExpect(jsonPath("$.page.totalElements", is(10)))
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
        final String selfLink = "http://localhost/physicians/search?page=0&size=20&sort=id,asc";

        MvcResult result = mockMvc.perform(get("/physicians/search").with(makeAuthorizedAdminUser())
                        .param("search", "Marcela")
                        .param("page", "0")
                        .param("size", "20")
                        .param("direction", "ASC")
                        .param("field", "id"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.page.totalElements", is(2)))
                .andExpect(jsonPath("$._embedded.physicianList", hasSize(2)))
                .andExpect(jsonPath("$._links.self.href", is(selfLink)))
                .andReturn();

        final String content = result.getResponse().getContentAsString();
        final int pageJsonKeySetSize = objectMapper.readValue(content, LinkedHashMap.class).keySet().size();

        assertEquals(3, pageJsonKeySetSize);
    }

    @Test
    void getPhysicianById() throws Exception {
        mockMvc.perform(get("/physicians/{id}", 2).with(makeAuthorizedAdminUser()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.firstName", is("João")))
                .andExpect(jsonPath("$.lastName", is("Brito")))
                .andExpect(jsonPath("$.specialty", is("DERMATOLOGIST")))
                .andExpect(jsonPath("$.appointments", hasSize(3)));
    }

    @Test
    void savePhysician() throws Exception {
        final Physician physician = new Physician();
        physician.setFirstName("Nelson");
        physician.setLastName("Muntz");
        physician.setSpecialty(Specialty.ENDOCRINOLOGIST);

        mockMvc.perform(post("/physicians").with(makeAuthorizedAdminUser())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(physician)))
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(101)))
                .andExpect(jsonPath("$.firstName", is("Nelson")))
                .andExpect(jsonPath("$.lastName", is("Muntz")))
                .andExpect(jsonPath("$.specialty", is("ENDOCRINOLOGIST")));
    }
}