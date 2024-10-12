package augusto108.ces.appointmenttracker.controllers;

import augusto108.ces.appointmenttracker.util.VersioningConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
class AppControllerTest
{

	private final WebApplicationContext context;
	private MockMvc mockMvc;

	@Autowired AppControllerTest(WebApplicationContext context)
	{
		this.context = context;
	}

	@BeforeEach
	void setUp()
	{
		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}

	@WithMockUser
	@Test
	void appIndex() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get(VersioningConstant.VERSION + "/"))
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/hal+json"))
			.andExpect(jsonPath("$", hasSize(4)));
	}
}
