package pl.migibud.conferenceroomreservationsystem.reservation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.migibud.conferenceroomreservationsystem.ConferenceroomreservationsystemApplication;
import pl.migibud.conferenceroomreservationsystem.conference.room.ConferenceRoom;
import pl.migibud.conferenceroomreservationsystem.conference.room.ConferenceRoomRepository;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.CreateReservationRequest;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static pl.migibud.conferenceroomreservationsystem.utils.JsonParserUtil.asJsonString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = ConferenceroomreservationsystemApplication.class)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class ReservationIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ConferenceRoomRepository conferenceRoomRepository;

    @Test
    void givenCreateReservationRequest_whenRegisterReservationAndStartDateIsAfterEndDate_thenReturnConflict() throws Exception {
        //given
        ConferenceRoom conferenceRoom = conferenceRoomRepository.save(new ConferenceRoom("Sala OSLO", "1.10", 1, true, 20));
        CreateReservationRequest request = new CreateReservationRequest(
                LocalDateTime.of(2022,12,13,12,30,00),
                LocalDateTime.of(2022,12,13,11,30,00),
                "res1",
                conferenceRoom.getId()
        );
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations").contentType(MediaType.APPLICATION_JSON).content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message[0]",equalTo("Reservation start date is after reservation end date")));
    }

    @Test
    void givenCreateReservationRequest_whenRegisterReservationAndDurationOfReservationIsLessThanMinValue_thenReturnBadRequest() throws Exception {
        //given
        ConferenceRoom conferenceRoom = conferenceRoomRepository.save(new ConferenceRoom("Sala OSLO", "1.10", 1, true, 20));
        CreateReservationRequest request = new CreateReservationRequest(
                LocalDateTime.of(2022,12,13,11,00,00),
                LocalDateTime.of(2022,12,13,11,14,00),
                "res1",
                conferenceRoom.getId()
        );
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations").contentType(MediaType.APPLICATION_JSON).content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message[0]",equalTo("Reservation duration is too short")));
    }

    @Test
    void givenCreateReservationRequest_whenRegisterReservationAndDurationOfReservationIsGreaterThanMaxValue_thenReturnBadRequest() throws Exception {
        //given
        ConferenceRoom conferenceRoom = conferenceRoomRepository.save(new ConferenceRoom("Sala OSLO", "1.10", 1, true, 20));
        CreateReservationRequest request = new CreateReservationRequest(
                LocalDateTime.of(2022,12,13,11,00,00),
                LocalDateTime.of(2022,12,13,13,01,00),
                "res1",
                conferenceRoom.getId()
        );
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations").contentType(MediaType.APPLICATION_JSON).content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message[0]",equalTo("Reservation duration is too long")));
    }

    @Test
    void givenCreateReservationRequest_whenRegisterReservationAndDateTimeAlreadyOccupied_thenReturnConflict() throws Exception {
        //given
        ConferenceRoom conferenceRoom = conferenceRoomRepository.save(new ConferenceRoom("Sala OSLO", "1.10", 1, true, 20));

        reservationRepository.save(new Reservation(
                LocalDateTime.of(2022,12,13,11,00,00),
                LocalDateTime.of(2022,12,13,12,00,00),
                "res1",
                conferenceRoom
        ));


        CreateReservationRequest request = new CreateReservationRequest(
                LocalDateTime.of(2022,12,13,11,00,00),
                LocalDateTime.of(2022,12,13,12,00,00),
                "res1",
                conferenceRoom.getId()
        );
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations").contentType(MediaType.APPLICATION_JSON).content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message[0]",equalTo("Reservation already exists at the requested time")));
    }

    @Test
    void givenCreateReservationRequest_whenRegisterReservationAndRequestStartDateIsBeforeReservationEndDate_thenReturnConflict() throws Exception {
        //given
        ConferenceRoom conferenceRoom = conferenceRoomRepository.save(new ConferenceRoom("Sala OSLO", "1.10", 1, true, 20));

        reservationRepository.save(new Reservation(
                LocalDateTime.of(2022,12,13,11,00,00),
                LocalDateTime.of(2022,12,13,12,00,00),
                "res1",
                conferenceRoom
        ));


        CreateReservationRequest request = new CreateReservationRequest(
                LocalDateTime.of(2022,12,13,11,59,00),
                LocalDateTime.of(2022,12,13,13,00,00),
                "res1",
                conferenceRoom.getId()
        );
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations").contentType(MediaType.APPLICATION_JSON).content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message[0]",equalTo("Reservation already exists at the requested time")));
    }

    @Test
    void givenCreateReservationRequest_whenRegisterReservationAndRequestEndDateIsAfterReservationStartDate_thenReturnConflict() throws Exception {
        //given
        ConferenceRoom conferenceRoom = conferenceRoomRepository.save(new ConferenceRoom("Sala OSLO", "1.10", 1, true, 20));

        reservationRepository.save(new Reservation(
                LocalDateTime.of(2022,12,13,12,00,00),
                LocalDateTime.of(2022,12,13,13,00,00),
                "res1",
                conferenceRoom
        ));


        CreateReservationRequest request = new CreateReservationRequest(
                LocalDateTime.of(2022,12,13,11,00,00),
                LocalDateTime.of(2022,12,13,12,01,00),
                "res1",
                conferenceRoom.getId()
        );
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations").contentType(MediaType.APPLICATION_JSON).content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message[0]",equalTo("Reservation already exists at the requested time")));
    }

    @Test
    void givenCreateReservationRequest_whenRegisterReservationAndRequestStartDateIsAfterReservationEndDate_thenReturnOk() throws Exception {
        //given
        ConferenceRoom conferenceRoom = conferenceRoomRepository.save(new ConferenceRoom("Sala OSLO", "1.10", 1, true, 20));

        reservationRepository.save(new Reservation(
                LocalDateTime.of(2022,12,13,12,00,00),
                LocalDateTime.of(2022,12,13,13,00,00),
                "res1",
                conferenceRoom
        ));


        CreateReservationRequest request = new CreateReservationRequest(
                LocalDateTime.of(2022,12,13,13,00,00),
                LocalDateTime.of(2022,12,13,14,00,00),
                "res1",
                conferenceRoom.getId()
        );
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations").contentType(MediaType.APPLICATION_JSON).content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",notNullValue()));
    }

    @Test
    void givenCreateReservationRequest_whenRegisterReservationAndRequestEndDateIsBeforeReservationStartDate_thenReturnOk() throws Exception {
        //given
        ConferenceRoom conferenceRoom = conferenceRoomRepository.save(new ConferenceRoom("Sala OSLO", "1.10", 1, true, 20));

        reservationRepository.save(new Reservation(
                LocalDateTime.of(2022,12,13,15,00,00),
                LocalDateTime.of(2022,12,13,16,00,00),
                "res1",
                conferenceRoom
        ));


        CreateReservationRequest request = new CreateReservationRequest(
                LocalDateTime.of(2022,12,13,13,00,00),
                LocalDateTime.of(2022,12,13,14,00,00),
                "res1",
                conferenceRoom.getId()
        );
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/reservations").contentType(MediaType.APPLICATION_JSON).content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",notNullValue()));
    }


}