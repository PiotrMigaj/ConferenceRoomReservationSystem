package pl.migibud.conferenceroomreservationsystem.conference.room.dto;

import pl.migibud.conferenceroomreservationsystem.organisation.dto.OrganisationView;

public interface ConferenceRoomView {
    String getId();
    String getName();
    String getIdentifier();
    int getLevel();
    boolean getAvailability();
    int getNumberOfSeats();
    OrganisationView getOrganisation();
}
