package pl.migibud.conferenceroomreservationsystem.conference.room.dto;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ConferenceRoomDto {
    private String id;
    private String name;
    private String identifier;
    private int level;
    private boolean availability;
    private int numberOfSeats;
}
