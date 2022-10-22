package pl.migibud.conferenceroomreservationsystem.exception.organisation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrganisationError {

	ORGANISATION_NOT_FOUND("Organisation does not exists"),
	ORGANISATION_ALREADY_EXISTS("Organisation already exists");

	private final String message;
}
