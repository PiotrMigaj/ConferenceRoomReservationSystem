package pl.migibud.conferenceroomreservationsystem.organisation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.migibud.conferenceroomreservationsystem.conference.room.ConferenceRoom;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class Organisation  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "{organisation.name.must.not.be.blank}")
    @Size(min=2,message = "{organisation.name.size.too_short}")
    @Size(max=20,message = "{organisation.name.size.too_long}")
    @Column(nullable = false,unique = true,length = 20)
    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonIgnore
    @OneToMany(mappedBy = "organisation")
    private Set<ConferenceRoom> conferenceRooms;

    public Organisation(String name) {
        this.name = name;
    }

    public Organisation(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public enum Status{
        ACTIVE,INACTIVE
    }

}
