package pl.migibud.conferenceroomreservationsystem.reservation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.migibud.conferenceroomreservationsystem.exception.organisation.OrganisationError;
import pl.migibud.conferenceroomreservationsystem.exception.reservation.ReservationError;
import pl.migibud.conferenceroomreservationsystem.organisation.OrganisationFacade;
import pl.migibud.conferenceroomreservationsystem.reservation.dto.CreateReservationRequest;
import pl.migibud.conferenceroomreservationsystem.utils.JsonParserUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.migibud.conferenceroomreservationsystem.utils.JsonParserUtil.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationFacade reservationFacade;
    @MockBean
    private ReservationQueryRepository reservationQueryRepository;

    @Test
    void givenInvalidReservationId_whenGetReservationById_thenStatusIsNotFound() throws Exception {
        //given
        String message = ReservationError.RESERVATION_NOT_FOUND.getMessage();
        when(reservationQueryRepository.findAllById(anyString())).thenReturn(Collections.emptyList());
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/reservations/"+ UUID.randomUUID().toString()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message[0]",equalTo(message)));
    }

    @Test
    void givenNullStartDate_whenRegisterReservation_thenReturnBadRequest() throws Exception {
        //given
        //when
        //then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new CreateReservationRequest(null, LocalDateTime.of(2022,12,13,12,30,0),"res1",UUID.randomUUID().toString()))))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verifyNoInteractions(reservationFacade);
    }

}