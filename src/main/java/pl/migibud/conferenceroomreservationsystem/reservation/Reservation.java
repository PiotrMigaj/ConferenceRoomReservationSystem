package pl.migibud.conferenceroomreservationsystem.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import pl.migibud.conferenceroomreservationsystem.conference.room.ConferenceRoom;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.CreateReservationRequest;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.ReservationDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Reservation {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reservationName;

    @ManyToOne
    @JoinColumn(name = "conference_room_id")
    @JsonIgnore
    private ConferenceRoom conferenceRoom;

    Reservation(LocalDateTime startDate, LocalDateTime endDate, String reservationName, ConferenceRoom conferenceRoom) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservationName = reservationName;
        this.conferenceRoom = conferenceRoom;
    }

    static Reservation of(CreateReservationRequest request,ConferenceRoom conferenceRoom){
        return new Reservation(
                request.getStartDate(),
                request.getEndDate(),
                request.getReservationName(),
                conferenceRoom
        );
    }

    ReservationDto toDto(){
        return ReservationDto.builder()
                .id(id)
                .startDate(startDate)
                .endDate(endDate)
                .reservationName(reservationName)
                .conferenceRoomId(conferenceRoom.getId())
                .build();
    }

}
