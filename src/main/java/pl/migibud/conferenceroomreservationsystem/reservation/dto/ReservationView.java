package pl.migibud.conferenceroomreservationsystem.reservation.dto;

import pl.migibud.conferenceroomreservationsystem.conference.room.dto.ConferenceRoomView;

import java.time.LocalDateTime;

public interface ReservationView {
    String getId();
    LocalDateTime getStartDate();
    LocalDateTime getEndDate();
    String getReservationName();
    ConferenceRoomView getConferenceRoom();
}
