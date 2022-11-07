package pl.migibud.conferenceroomreservationsystem.exception.reservation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationError {

	RESERVATION_NOT_FOUND("Reservation does not exists"),
	RESERVATION_ALREADY_EXISTS("Reservation already exists at the requested time"),
	RESERVATION_DURATION_TOO_SHORT("Reservation duration is too short"),
	RESERVATION_DURATION_TOO_LONG("Reservation duration is too long"),
	RESERVATION_DATE_TIME_MISMATCH("Reservation start date is after reservation end date");

	private final String message;
}
