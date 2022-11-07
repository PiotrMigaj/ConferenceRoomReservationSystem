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
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;
    @Size(min=2,max=20)
    private String reservationName;
    private String conferenceRoomId;
}
