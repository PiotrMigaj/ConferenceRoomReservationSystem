package pl.migibud.conferenceroomreservationsystem.exception.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.migibud.conferenceroomreservationsystem.exception.organisation.OrganisationError;

@AllArgsConstructor
@Getter
public class ReservationException extends RuntimeException{

	private ReservationError reservationError;
}
