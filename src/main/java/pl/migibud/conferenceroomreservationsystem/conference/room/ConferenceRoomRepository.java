package pl.migibud.conferenceroomreservationsystem.conference.room;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.migibud.conferenceroomreservationsystem.organisation.Organisation;

import java.util.Optional;

interface ConferenceRoomRepository {
    boolean existsByNameAndOrganisation_Id(String name,Long organisationId);
    boolean existsByIdentifierAndOrganisation_Id(String identifier,Long organisationId);
    ConferenceRoom save(ConferenceRoom conferenceRoom);
    Optional<ConferenceRoom> findById(String id);
    void delete(ConferenceRoom conferenceRoom);
    Optional<ConferenceRoom> findByNameAndOrganisation_Id(String name,Long organisationId);
}

interface SqlConferenceRoomRepository extends ConferenceRoomRepository, JpaRepository<ConferenceRoom,String>{
}
