package pl.migibud.conferenceroomreservationsystem.reservation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.migibud.conferenceroomreservationsystem.conference.room.ConferenceRoom;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ReservationRepositoryTest {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    TestEntityManager testEntityManager;

    @Test
    void givenReservation_whenSaveReservationInBd_thenReturnSavedOrganisation() {
        //given
        ConferenceRoom conferenceRoom = new ConferenceRoom("Sala OSLO", "1.20", 0, true, 20);
        testEntityManager.persist(conferenceRoom);
        Reservation reservation = new Reservation(
                LocalDateTime.of(2022, 11, 9, 12, 0, 0),
                LocalDateTime.of(2022, 11, 9, 13, 0, 0),
                "PMIG stand up",
                conferenceRoom);
        //when
        reservationRepository.save(reservation);
        //then
        assertThat(reservation.getId()).isNotNull();
    }

    @Test
    void givenReservationId_whenFindById_thenReturnReservationFromDb() {
        //given
        List<Reservation> reservations = Arrays.asList(new Reservation(
                        LocalDateTime.of(2022, 10, 9, 10, 30, 00),
                        LocalDateTime.of(2022, 10, 9, 11, 30, 00),
                        "r1",
                        null),
                new Reservation(
                        LocalDateTime.of(2022, 11, 9, 10, 30, 00),
                        LocalDateTime.of(2022, 11, 9, 11, 30, 00),
                        "r2",
                        null),
                new Reservation(
                        LocalDateTime.of(2022, 12, 9, 10, 30, 00),
                        LocalDateTime.of(2022, 12, 9, 11, 30, 00),
                        "r3",
                        null));
        reservations.forEach(testEntityManager::persist);

        Reservation reservation = new Reservation(
                LocalDateTime.of(2023, 12, 9, 10, 30, 00),
                LocalDateTime.of(2023, 12, 9, 11, 30, 00),
                "r3",
                null);

        testEntityManager.persist(reservation);

        //when
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservation.getId());
        //then
        assertThat(reservationOptional).isNotEmpty();
    }

    @Test
    void givenReservationId_whenFindByIdValueThatNotExists_thenReturnEmptyOptional() {
        //given
        List<Reservation> reservations = Arrays.asList(new Reservation(
                        LocalDateTime.of(2022, 10, 9, 10, 30, 00),
                        LocalDateTime.of(2022, 10, 9, 11, 30, 00),
                        "r1",
                        null),
                new Reservation(
                        LocalDateTime.of(2022, 11, 9, 10, 30, 00),
                        LocalDateTime.of(2022, 11, 9, 11, 30, 00),
                        "r2",
                        null),
                new Reservation(
                        LocalDateTime.of(2022, 12, 9, 10, 30, 00),
                        LocalDateTime.of(2022, 12, 9, 11, 30, 00),
                        "r3",
                        null));
        reservations.forEach(testEntityManager::persist);

        //when
        Optional<Reservation> reservationOptional = reservationRepository.findById(UUID.randomUUID().toString());
        //then
        assertThat(reservationOptional).isEmpty();
    }

    @Test
    void givenReservationToDelete_whenDeleteReservation_thenDeleteReservation() {
        //given
        Reservation reservation = new Reservation(
                LocalDateTime.of(2022, 12, 9, 10, 30, 00),
                LocalDateTime.of(2022, 12, 9, 11, 30, 00),
                "r3",
                null);
        testEntityManager.persist(reservation);

        //when
        reservationRepository.delete(reservation);
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservation.getId());
        //then
        assertThat(reservationOptional).isEmpty();
    }

    @Test
    void givenStartAndEndTimeAndOrganisationId_whenFindByConferenceRoom_IdAndStartDateLessThanAndEndDateGreaterThan_thenReturnEmptyOptional(){
        //given
        ConferenceRoom conferenceRoom = new ConferenceRoom("Sala OSLO", "1.20", 0, true, 20);
        testEntityManager.persist(conferenceRoom);
        Reservation reservation = new Reservation(
                LocalDateTime.of(2022, 11, 9, 12, 0, 0),
                LocalDateTime.of(2022, 11, 9, 13, 0, 0),
                "PMIG stand up",
                conferenceRoom);
        testEntityManager.persist(reservation);
        //when
        Optional<Reservation> optionalReservation = reservationRepository.findByConferenceRoom_IdAndStartDateLessThanAndEndDateGreaterThan(conferenceRoom.getId(), LocalDateTime.of(2022, 11, 9, 12, 01, 0), LocalDateTime.of(2022, 11, 9, 12, 59, 0));
        //then
        System.out.println(optionalReservation.toString());
        assertThat(optionalReservation).isEmpty();
    }


}