package pl.migibud.conferenceroomreservationsystem.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.ReservationView;

import java.util.List;
import java.util.Optional;

public interface ReservationQueryRepository {
    Page<ReservationView> findAllBy(Pageable pageable);
    Page<ReservationView> findAllByConferenceRoom_Id(Pageable pageable,String id);
    List<ReservationView> findAllById(String id);
}

interface SqlReservationQueryRepository extends ReservationQueryRepository, JpaRepository<Reservation,String>{


}
