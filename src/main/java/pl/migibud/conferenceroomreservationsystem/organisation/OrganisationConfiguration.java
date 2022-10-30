package pl.migibud.conferenceroomreservationsystem.organisation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OrganisationConfiguration {

    @Bean
    OrganisationFacade organisationFacade(OrganisationRepository organisationRepository){
        return new OrganisationFacade(organisationRepository);
    }
}
