package pl.migibud.conferenceroomreservationsystem.reservation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.migibud.conferenceroomreservationsystem.conference.room.ConferenceRoomQueryRepository;

@Configuration
class ReservationConfiguration {

    @Bean
    ReservationFacade reservationFacade(ReservationRepository reservationRepository, ConferenceRoomQueryRepository conferenceRoomQueryRepository){
        return new ReservationFacade(reservationRepository,conferenceRoomQueryRepository);
    }
}
