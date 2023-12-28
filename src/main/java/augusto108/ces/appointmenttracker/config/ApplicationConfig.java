package augusto108.ces.appointmenttracker.config;

import augusto108.ces.appointmenttracker.model.entities.BaseEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PagedResourcesAssembler;

@Configuration
public class ApplicationConfig {

    @Bean(name = "configPagedResourcesAssemblerBean")
    public PagedResourcesAssembler<? extends BaseEntity> pagedResourcesAssembler() {
        return new PagedResourcesAssembler<>(null, null);
    }
}
