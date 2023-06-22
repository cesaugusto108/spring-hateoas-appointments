package augusto108.ces.appointmenttracker.controllers.handler;

import augusto108.ces.appointmenttracker.controllers.AuthorizeAdminUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("sec")
class ApplicationExceptionHandlerTest extends AuthorizeAdminUser {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

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
    void handleNotFoundException() throws Exception {
        mockMvc.perform(get("/appointments/{id}", 0).with(makeAuthorizedAdminUser()))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.statusCode", is(404)));
    }

    @Test
    void handleBadRequest() throws Exception {
        final String error = "java.lang.NumberFormatException: For input string: \"aaa\"";
        final String message = "Wrong property format: For input string: \"aaa\"";

        mockMvc.perform(get("/appointments/aaa").with(makeAuthorizedAdminUser()))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.error", is(String.format("%s", error))))
                .andExpect(jsonPath("$.message", is(String.format("%s", message))))
                .andExpect(jsonPath("$.status", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.statusCode", is(400)));
    }
}