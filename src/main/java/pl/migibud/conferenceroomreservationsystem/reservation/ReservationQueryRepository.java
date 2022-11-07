package pl.migibud.conferenceroomreservationsystem.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.ReservationDto;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.ReservationView;

import java.util.Optional;

public interface ReservationQueryRepository {
    Page<ReservationView> findAllBy(Pageable pageable);
}

interface SqlReservationQueryRepository extends ReservationQueryRepository, JpaRepository<Reservation,String>{
}
