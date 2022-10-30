package pl.migibud.conferenceroomreservationsystem.conference.room;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.migibud.conferenceroomreservationsystem.conference.room.dto.ConferenceRoomDto;
import pl.migibud.conferenceroomreservationsystem.conference.room.dto.CreateConferenceRoomRequest;
import pl.migibud.conferenceroomreservationsystem.exception.conference.room.ConferenceRoomError;
import pl.migibud.conferenceroomreservationsystem.exception.conference.room.ConferenceRoomException;
import pl.migibud.conferenceroomreservationsystem.exception.organisation.OrganisationError;
import pl.migibud.conferenceroomreservationsystem.exception.organisation.OrganisationException;
import pl.migibud.conferenceroomreservationsystem.organisation.Organisation;
import pl.migibud.conferenceroomreservationsystem.organisation.OrganisationRepository;

@RequiredArgsConstructor
public class ConferenceRoomFacade {

    private final OrganisationRepository organisationRepository;
    private final ConferenceRoomRepository conferenceRoomRepository;

    @Transactional
    public ConferenceRoomDto registerConferenceRoom(CreateConferenceRoomRequest request) {

        if (conferenceRoomRepository.existsByNameAndOrganisation_Id(request.getName(), request.getOrganisationId())) {
            throw new ConferenceRoomException(ConferenceRoomError.CONFERENCE_ROOM_NAME_NOT_UNIQUE_FOR_ORGANISATION);
        }
        Organisation organisation = organisationRepository.findByIdAndAndStatus(request.getOrganisationId(), Organisation.Status.ACTIVE)
                .orElseThrow(() -> new OrganisationException(OrganisationError.ORGANISATION_NOT_FOUND));

        return conferenceRoomRepository.save(ConferenceRoom.of(request, organisation)).toDto();
    }

    @Transactional
    public void deleteConferenceRoomById(String conferenceRoomId) {
        ConferenceRoom conferenceRoom = conferenceRoomRepository.findById(conferenceRoomId)
                .orElseThrow(() -> new ConferenceRoomException(ConferenceRoomError.CONFERENCE_ROOM_NOT_FOUND));
        conferenceRoomRepository.delete(conferenceRoom);
    }

    @Transactional
    public ConferenceRoomDto updateConferenceRoom(String id, CreateConferenceRoomRequest createConferenceRoomRequest) {

        conferenceRoomRepository.findByNameAndOrganisation_Id(createConferenceRoomRequest.getName(), createConferenceRoomRequest.getOrganisationId())
                .ifPresent(
                        conferenceRoom -> {
                            throw new ConferenceRoomException(ConferenceRoomError.CONFERENCE_ROOM_ALREADY_EXISTS);
                        });



        return conferenceRoomRepository.findById(id)
                .map(conferenceRoom -> {
                    conferenceRoom.setName(createConferenceRoomRequest.getName()!=null?createConferenceRoomRequest.getName(): conferenceRoom.getName());
                    conferenceRoom.setIdentifier(createConferenceRoomRequest.getIdentifier()!=null? createConferenceRoomRequest.getIdentifier() : conferenceRoom.getIdentifier());
                    conferenceRoom.setLevel(createConferenceRoomRequest.getLevel()!=null? createConferenceRoomRequest.getLevel() : conferenceRoom.getLevel());
                    conferenceRoom.setAvailability(createConferenceRoomRequest.getAvailability()!=null? createConferenceRoomRequest.getAvailability() : conferenceRoom.getAvailability());
                    conferenceRoom.setNumberOfSeats(createConferenceRoomRequest.getNumberOfSeats()!=null? createConferenceRoomRequest.getNumberOfSeats() : conferenceRoom.getNumberOfSeats());
                    return conferenceRoom.toDto();
                })
                .orElseThrow(() -> new ConferenceRoomException(ConferenceRoomError.CONFERENCE_ROOM_NOT_FOUND));
    }
}
