package pl.migibud.conferenceroomreservationsystem.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import pl.migibud.conferenceroomreservationsystem.conference.room.ConferenceRoom;
import pl.migibud.conferenceroomreservationsystem.conference.room.ConferenceRoomQueryRepository;
import pl.migibud.conferenceroomreservationsystem.exception.conference.room.ConferenceRoomError;
import pl.migibud.conferenceroomreservationsystem.exception.conference.room.ConferenceRoomException;
import pl.migibud.conferenceroomreservationsystem.exception.reservation.ReservationError;
import pl.migibud.conferenceroomreservationsystem.exception.reservation.ReservationException;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.CreateReservationRequest;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.ReservationDto;

import java.time.temporal.ChronoUnit;

@Slf4j
@RequiredArgsConstructor
public class ReservationFacade {

    @Value("${values.reservation.minimum.time.reservation.in.minutes}")
    private long minimumDurationOfTimeReservationInMinutes;
    @Value("${values.reservation.maximum.time.reservation.in.minutes}")
    private long maximumDurationOfTimeReservationInMinutes;
    private final ReservationRepository reservationRepository;
    private final ConferenceRoomQueryRepository conferenceRoomQueryRepository;

    @Transactional
    public ReservationDto registerReservation(CreateReservationRequest request) {
        validateDateTimeOfReservationRequest(request);

        ConferenceRoom conferenceRoom = conferenceRoomQueryRepository.findById(request.getConferenceRoomId())
                .orElseThrow(() -> new ConferenceRoomException(ConferenceRoomError.CONFERENCE_ROOM_NOT_FOUND));

        reservationRepository.findByConferenceRoom_IdAndStartDateLessThanAndEndDateGreaterThan(request.getConferenceRoomId(),request.getEndDate(),request.getStartDate())
                .ifPresent(reservation -> {
                    throw new ReservationException(ReservationError.RESERVATION_ALREADY_EXISTS);
                });

        return reservationRepository.save(Reservation.of(request,conferenceRoom)).toDto();
    }

    @Transactional
    public ReservationDto updateReservation(String reservationId, CreateReservationRequest request){

        validateDateTimeOfReservationRequest(request);

        Reservation reservationToBeUpdated = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ReservationError.RESERVATION_NOT_FOUND));

        ConferenceRoom conferenceRoom = conferenceRoomQueryRepository.findById(request.getConferenceRoomId())
                .orElseThrow(() -> new ConferenceRoomException(ConferenceRoomError.CONFERENCE_ROOM_NOT_FOUND));

        if (reservationToBeUpdated.getStartDate().isEqual(request.getStartDate())&&reservationToBeUpdated.getEndDate().isEqual(request.getEndDate())&&conferenceRoom.getId().equals(request.getConferenceRoomId())){
            reservationToBeUpdated.setReservationName(request.getReservationName());
            return reservationToBeUpdated.toDto();
        }

        reservationRepository.findByConferenceRoom_IdAndStartDateLessThanAndEndDateGreaterThan(request.getConferenceRoomId(),request.getEndDate(),request.getStartDate())
                .ifPresent(reservation -> {
                    throw new ReservationException(ReservationError.RESERVATION_ALREADY_EXISTS);
                });

        reservationToBeUpdated.setReservationName(request.getReservationName());
        reservationToBeUpdated.setConferenceRoom(conferenceRoom);
        reservationToBeUpdated.setStartDate(request.getStartDate());
        reservationToBeUpdated.setEndDate(request.getEndDate());

        return reservationToBeUpdated.toDto();
    }

    @Transactional
    ReservationDto updateReservationName(String reservationId, CreateReservationRequest request) {

        Reservation reservationToBeUpdated = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ReservationError.RESERVATION_NOT_FOUND));
        reservationToBeUpdated.setReservationName(request.getReservationName());
        return reservationToBeUpdated.toDto();
    }

    void deleteReservationById(String reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException(ReservationError.RESERVATION_NOT_FOUND));
        reservationRepository.delete(reservation);
    }

    private void validateDateTimeOfReservationRequest(CreateReservationRequest request) {
        long minutes = ChronoUnit.MINUTES.between(request.getStartDate(), request.getEndDate());

        if (minutes<0){
            throw new ReservationException(ReservationError.RESERVATION_DATE_TIME_MISMATCH);
        }
        if (minutes<minimumDurationOfTimeReservationInMinutes){
            throw new ReservationException(ReservationError.RESERVATION_DURATION_TOO_SHORT);
        }
        if (minutes>maximumDurationOfTimeReservationInMinutes){
            throw new ReservationException(ReservationError.RESERVATION_DURATION_TOO_LONG);
        }
    }
}
