package pl.migibud.conferenceroomreservationsystem.conference.room;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.migibud.conferenceroomreservationsystem.organisation.OrganisationRepository;

@Configuration
class ConferenceRoomConfiguration {

    @Bean
     ConferenceRoomFacade conferenceFacade(OrganisationRepository organisationRepository, ConferenceRoomRepository conferenceRoomRepository){
         return new ConferenceRoomFacade(organisationRepository,conferenceRoomRepository);
     }
}
