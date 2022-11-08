package pl.migibud.conferenceroomreservationsystem.reservation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.migibud.conferenceroomreservationsystem.conference.room.ConferenceRoom;
import pl.migibud.conferenceroomreservationsystem.conference.room.ConferenceRoomQueryRepository;
import pl.migibud.conferenceroomreservationsystem.exception.conference.room.ConferenceRoomException;
import pl.migibud.conferenceroomreservationsystem.exception.reservation.ReservationException;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.CreateReservationRequest;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.ReservationDto;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


@ExtendWith(SpringExtension.class)
//FIXME Must consider values given in application.yaml
@TestPropertySource(properties = {"values.reservation.minimum.time.reservation.in.minutes=15","values.reservation.maximum.time.reservation.in.minutes=120"})
class ReservationFacadeTest {

    @TestConfiguration
    static class ReservationFacadeTestConfiguration{
        @Bean
        ReservationFacade reservationFacade(ReservationRepository reservationRepository, ConferenceRoomQueryRepository conferenceRoomQueryRepository){
            return new ReservationFacade(reservationRepository,conferenceRoomQueryRepository);
        }
    }

    @Autowired
    ReservationFacade reservationFacade;

    @MockBean
    ReservationRepository reservationRepository;

    @MockBean
    ConferenceRoomQueryRepository conferenceRoomQueryRepository;

    @Test
    void givenCreateReservationRequest_whenStartDateIsAfterEndDate_thenThrowException(){
        //given
        CreateReservationRequest request = new CreateReservationRequest(
                LocalDateTime.of(2022,12,13,13,0,0),
                LocalDateTime.of(2022,12,13,12,0,0),
                "res1",
                null
        );
        //when
        //then
        assertThatThrownBy(()->reservationFacade.registerReservation(request))
                .isNotNull()
                .isInstanceOf(ReservationException.class);
    }

    @Test
    void givenCreateReservationRequest_whenReservationDurationIsLessThanMinValue_thenThrowException(){
        //given
        CreateReservationRequest request = new CreateReservationRequest(
                LocalDateTime.of(2022,12,13,12,0,0),
                LocalDateTime.of(2022,12,13,12,14,0),
                "res1",
                null
        );
        //when
        //then
        assertThatThrownBy(()->reservationFacade.registerReservation(request))
                .isNotNull()
                .isInstanceOf(ReservationException.class);
    }

    @Test
    void givenCreateReservationRequest_whenReservationDurationIsGreaterThanMaxValue_thenThrowException(){
        //given
        CreateReservationRequest request = new CreateReservationRequest(
                LocalDateTime.of(2022,12,13,12,0,0),
                LocalDateTime.of(2022,12,13,14,1,0),
                "res1",
                null
        );
        //when
        //then
        assertThatThrownBy(()->reservationFacade.registerReservation(request))
                .isNotNull()
                .isInstanceOf(ReservationException.class);
    }

    @Test
    void givenCreateReservationRequest_whenConferenceRoomDoesNotExists_thenThrowException(){
        //given
        CreateReservationRequest request = new CreateReservationRequest(
                LocalDateTime.of(2022,12,13,12,0,0),
                LocalDateTime.of(2022,12,13,13,0,0),
                "res1",
                "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"
        );
        Mockito.when(conferenceRoomQueryRepository.findById(request.getConferenceRoomId())).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(()->reservationFacade.registerReservation(request))
                .isNotNull()
                .isInstanceOf(ConferenceRoomException.class);
    }

    @Test
    void givenCreateReservationRequest_whenReservationAlreadyExists_thenThrowException(){
        //given
        CreateReservationRequest request = new CreateReservationRequest(
                LocalDateTime.of(2022,12,13,12,0,0),
                LocalDateTime.of(2022,12,13,13,0,0),
                "res1",
                "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"
        );
        Mockito.when(conferenceRoomQueryRepository.findById(request.getConferenceRoomId())).thenReturn(Optional.of(new ConferenceRoom("Sala OSLO", "1.20", 0, true, 20)));
        Mockito.when(reservationRepository.findByConferenceRoom_IdAndStartDateLessThanAndEndDateGreaterThan(anyString(),any(),any())).thenReturn(Optional.of(new Reservation(LocalDateTime.of(2022,12,14,12,0,0),LocalDateTime.of(2022,12,14,12,0,0),"res1",null)));
        //when
        //then
        assertThatThrownBy(()->reservationFacade.registerReservation(request))
                .isNotNull()
                .isInstanceOf(ReservationException.class);
    }

    @Test
    void givenCreateReservationRequest_whenGivenDataAreValid_thenSaveTheReservation(){
        //given
        CreateReservationRequest request = new CreateReservationRequest(
                LocalDateTime.of(2022,12,13,12,0,0),
                LocalDateTime.of(2022,12,13,13,0,0),
                "res1",
                "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454"
        );
        Mockito.when(conferenceRoomQueryRepository.findById(request.getConferenceRoomId())).thenReturn(Optional.of(new ConferenceRoom(request.getConferenceRoomId(),"Sala OSLO", "1.20", 0, true, 20)));
        Mockito.when(reservationRepository.findByConferenceRoom_IdAndStartDateLessThanAndEndDateGreaterThan(anyString(),any(),any())).thenReturn(Optional.empty());
        Mockito.when(reservationRepository.save(any())).thenReturn(new Reservation(
                request.getStartDate(),
                request.getEndDate(),
                request.getReservationName(),
                new ConferenceRoom(request.getConferenceRoomId(),"Sala OSLO", "1.20", 0, true, 20)
        ));
        //when
        ReservationDto reservationDto = reservationFacade.registerReservation(request);
        //then
        Mockito.verify(reservationRepository,Mockito.times(1)).save(any());
        assertThat(reservationDto.getConferenceRoomId()).isEqualTo(request.getConferenceRoomId());
    }

}