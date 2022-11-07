package pl.migibud.conferenceroomreservationsystem.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

interface ReservationRepository {
    Reservation save(Reservation reservation);
}

interface SqlReservationRepository extends ReservationRepository, JpaRepository<Reservation,String>{
}
