package pl.migibud.conferenceroomreservationsystem.exception.conference.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.migibud.conferenceroomreservationsystem.exception.organisation.OrganisationError;

@AllArgsConstructor
@Getter
public class ConferenceRoomException extends RuntimeException{

	private ConferenceRoomError conferenceRoomError;
}
