package pl.migibud.conferenceroomreservationsystem.reservation;

import lombok.RequiredArgsConstructor;
import pl.migibud.conferenceroomreservationsystem.conference.room.ConferenceRoom;
import pl.migibud.conferenceroomreservationsystem.conference.room.ConferenceRoomQueryRepository;
import pl.migibud.conferenceroomreservationsystem.exception.conference.room.ConferenceRoomError;
import pl.migibud.conferenceroomreservationsystem.exception.conference.room.ConferenceRoomException;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.CreateReservationRequest;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.ReservationDto;

@RequiredArgsConstructor
public class ReservationFacade {

    private final ReservationRepository reservationRepository;
    private final ConferenceRoomQueryRepository conferenceRoomQueryRepository;

    ReservationDto registerReservation(CreateReservationRequest request) {

        ConferenceRoom conferenceRoom = conferenceRoomQueryRepository.findById(request.getConferenceRoomId())
                .orElseThrow(() -> new ConferenceRoomException(ConferenceRoomError.CONFERENCE_ROOM_NOT_FOUND));

        return reservationRepository.save(Reservation.of(request,conferenceRoom)).toDto();
    }
}
