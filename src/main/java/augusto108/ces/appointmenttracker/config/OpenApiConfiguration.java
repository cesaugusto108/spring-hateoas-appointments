package augusto108.ces.appointmenttracker.config;

import augusto108.ces.appointmenttracker.util.VersioningConstant;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI openAPIconfig() {
        final Info info = new Info();
        final String version = VersioningConstant.VERSION.substring(5, 7);

        return new OpenAPI()
                .info(info.version(version).title("Appointment tracker").description("Appointments managing system"));
    }
}