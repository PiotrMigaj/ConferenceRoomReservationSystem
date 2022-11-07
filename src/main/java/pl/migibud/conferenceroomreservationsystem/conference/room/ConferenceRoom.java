package pl.migibud.conferenceroomreservationsystem.conference.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pl.migibud.conferenceroomreservationsystem.conference.room.dto.ConferenceRoomDto;
import pl.migibud.conferenceroomreservationsystem.conference.room.dto.CreateConferenceRoomRequest;
import pl.migibud.conferenceroomreservationsystem.organisation.Organisation;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ConferenceRoom {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;
    private String name;
    @Pattern(regexp = "^\\d\\.\\d{2}$",message = "{conference.room.identifier.must.fit.pattern.'d.dd'.where.'d equals digit'}")
    private String identifier;
    private Integer level;
    private Boolean availability;
    @Min(0)
    @Max(30)
    private Integer numberOfSeats;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    @EqualsAndHashCode.Exclude
    private Organisation organisation;

    private ConferenceRoom(String name, String identifier, int level, boolean availability, int numberOfSeats, Organisation organisation) {
        this.name = name;
        this.identifier = identifier;
        this.level = level;
        this.availability = availability;
        this.numberOfSeats = numberOfSeats;
        this.organisation = organisation;
    }

    private ConferenceRoom(String name, String identifier, int level, boolean availability, int numberOfSeats) {
        this.name = name;
        this.identifier = identifier;
        this.level = level;
        this.availability = availability;
        this.numberOfSeats = numberOfSeats;
    }

    public static ConferenceRoom of(CreateConferenceRoomRequest request, Organisation organisation){
        return new ConferenceRoom(
                request.getName(),
                request.getIdentifier(),
                request.getLevel(),
                request.getAvailability(),
                request.getNumberOfSeats(),
                organisation
        );
    }

    public static ConferenceRoom of(CreateConferenceRoomRequest request){
        return new ConferenceRoom(
                request.getName(),
                request.getIdentifier(),
                request.getLevel(),
                request.getAvailability(),
                request.getNumberOfSeats()
        );
    }

    ConferenceRoomDto toDto(){
        return ConferenceRoomDto.builder()
                .id(id)
                .name(name)
                .identifier(identifier)
                .level(level)
                .availability(availability)
                .numberOfSeats(numberOfSeats)
                .organisationId(organisation.getId())
                .build();
    }

}
