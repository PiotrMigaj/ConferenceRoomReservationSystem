package pl.migibud.conferenceroomreservationsystem.exception.organisation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrganisationException extends RuntimeException{

	private OrganisationError organisationError;
}
