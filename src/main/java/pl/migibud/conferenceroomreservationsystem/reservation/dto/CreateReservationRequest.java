package pl.migibud.conferenceroomreservationsystem.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class CreateReservationRequest {
    @NotNull(groups = {AddReservation.class,UpdateReservation.class},message = "Start date of the reservation can not be null")
    private LocalDateTime startDate;
    @NotNull(groups = {AddReservation.class,UpdateReservation.class},message = "End date of the reservation can not be null")
    private LocalDateTime endDate;
    @Size(min=2,max=20,groups = {AddReservation.class,UpdateReservation.class,UpdateReservationName.class},message = "Number of characters of reservation name must be between ")
    private String reservationName;
    private String conferenceRoomId;

    public static interface AddReservation{};
    public static interface UpdateReservation{};
    public static interface UpdateReservationName{};
}
