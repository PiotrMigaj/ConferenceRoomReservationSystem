package pl.migibud.conferenceroomreservationsystem.conference.room;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConferenceRoomRepository {
    boolean existsByNameAndOrganisation_Id(String name,Long organisationId);
    boolean existsByIdentifierAndOrganisation_Id(String identifier,Long organisationId);
    ConferenceRoom save(ConferenceRoom conferenceRoom);
    Optional<ConferenceRoom> findById(String id);
    void delete(ConferenceRoom conferenceRoom);
    Optional<ConferenceRoom> findByNameAndOrganisation_Id(String name,Long organisationId);
    void deleteAll();
}
interface SqlConferenceRoomRepository extends ConferenceRoomRepository, JpaRepository<ConferenceRoom,String>{
}
