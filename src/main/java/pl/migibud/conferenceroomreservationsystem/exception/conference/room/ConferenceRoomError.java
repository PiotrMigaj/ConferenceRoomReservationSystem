package pl.migibud.conferenceroomreservationsystem.exception.conference.room;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConferenceRoomError {

	CONFERENCE_ROOM_NOT_FOUND("Conference room does not exists"),
	CONFERENCE_ROOM_NAME_NOT_UNIQUE_FOR_ORGANISATION("Conference room name must be unique for organisation"),
	CONFERENCE_ROOM_ALREADY_EXISTS("Conference room already exists for given organisation");


	private final String message;
}
