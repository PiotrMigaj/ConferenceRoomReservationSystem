package pl.migibud.conferenceroomreservationsystem.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.migibud.conferenceroomreservationsystem.conference.room.dto.ConferenceRoomDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ReservationDto {
    private String id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reservationName;
    private String conferenceRoomId;
}
