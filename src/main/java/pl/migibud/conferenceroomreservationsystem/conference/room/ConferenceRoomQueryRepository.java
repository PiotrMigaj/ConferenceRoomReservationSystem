package pl.migibud.conferenceroomreservationsystem.conference.room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.migibud.conferenceroomreservationsystem.conference.room.dto.ConferenceRoomDto;

import java.util.Optional;

public interface ConferenceRoomQueryRepository {
    Page<ConferenceRoomDto> findAllBy(Pageable pageable);
    @Query("SELECT new pl.migibud.conferenceroomreservationsystem.conference.room.dto.ConferenceRoomDto(" +
            "c.id," +
            "c.name," +
            "c.identifier," +
            "c.level," +
            "c.availability," +
            "c.numberOfSeats) " +
            "FROM ConferenceRoom c WHERE c.id=?1")
    Optional<ConferenceRoomDto> getByIdBy(String id);
}

interface SqlConferenceRoomQueryRepository extends ConferenceRoomQueryRepository, JpaRepository<ConferenceRoom,String>{
}
