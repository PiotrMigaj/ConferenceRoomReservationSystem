package pl.migibud.conferenceroomreservationsystem.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

interface ReservationRepository {
    Reservation save(Reservation reservation);
    void delete(Reservation reservation);
    Optional<Reservation> findById(String id);
    Optional<Reservation> findByConferenceRoom_IdAndStartDateLessThanAndEndDateGreaterThan(String conferenceRoomId, LocalDateTime endDate,LocalDateTime startDate);
}

interface SqlReservationRepository extends ReservationRepository, JpaRepository<Reservation,String>{
}
