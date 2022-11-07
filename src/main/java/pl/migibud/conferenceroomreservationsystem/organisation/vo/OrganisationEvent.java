package pl.migibud.conferenceroomreservationsystem.organisation.vo;

import lombok.Data;
import pl.migibud.conferenceroomreservationsystem.domain.event.DomainEvent;
import pl.migibud.conferenceroomreservationsystem.organisation.Organisation;

import java.time.Instant;

@Data
public class OrganisationEvent implements DomainEvent {

    private final Instant occurredOn;
    private final Data data;

    @Override
    public Instant getOccurredOn() {
        return null;
    }

    @lombok.Data
    public static class Data{
        private final Organisation organisation;
    }
}
