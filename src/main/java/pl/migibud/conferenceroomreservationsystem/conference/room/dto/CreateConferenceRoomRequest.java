package pl.migibud.conferenceroomreservationsystem.conference.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CreateConferenceRoomRequest {
    @NotBlank
    private String name;
    @Pattern(regexp = "^\\d\\.\\d{2}$",message = "{conference.room.identifier.must.fit.pattern.'d.dd'.where.'d equals digit'}")
    @NotNull
    private String identifier;
    private Integer level;
    private Boolean availability;
    @Min(0)
    @Max(30)
    private Integer numberOfSeats;
    @NotNull
    private Long organisationId;
}
