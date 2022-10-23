package pl.migibud.conferenceroomreservationsystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.migibud.conferenceroomreservationsystem.organisation.OrganisationFacade;
import pl.migibud.conferenceroomreservationsystem.organisation.OrganisationRepository;

@Configuration
class BeanConfiguration {

    @Bean
    OrganisationFacade organisationService(OrganisationRepository organisationRepository){
        return new OrganisationFacade(organisationRepository);
    }
}
