package augusto108.ces.appointmenttracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.bind.v2.TODO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("sec")
class AppointmentControllerTest extends AuthorizeAdminUser {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private ObjectMapper objectMapper;

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
        mockMvc.perform(get(""))
                .andExpect(status().isOk());
        // TODO complete this test
    }

    @Test
    void saveAppointment() {
    }

    @Test
    void confirmAppointment() {
    }

    @Test
    void finishAppointment() {
    }

    @Test
    void cancelAppointment() {
    }
}