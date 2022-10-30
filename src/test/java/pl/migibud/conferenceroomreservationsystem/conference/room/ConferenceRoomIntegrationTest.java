package pl.migibud.conferenceroomreservationsystem.conference.room;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
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
import pl.migibud.conferenceroomreservationsystem.conference.room.dto.CreateConferenceRoomRequest;
import pl.migibud.conferenceroomreservationsystem.organisation.Organisation;
import pl.migibud.conferenceroomreservationsystem.organisation.OrganisationRepository;

import static org.hamcrest.core.IsEqual.equalTo;
import static pl.migibud.conferenceroomreservationsystem.utils.JsonParserUtil.asJsonString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = ConferenceroomreservationsystemApplication.class)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class ConferenceRoomIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ConferenceRoomRepository conferenceRoomRepository;
    @Autowired
    OrganisationRepository organisationRepository;

    @Test
    void givenCreateConferenceRoomRequest_whenRegisterConferenceRoom_thenRegisterConferenceRoomAndReturnCreated() throws Exception {
        //given
        Organisation organisation = new Organisation("PG-Projekt", Organisation.Status.ACTIVE);
        organisationRepository.save(organisation);

        CreateConferenceRoomRequest request = CreateConferenceRoomRequest.builder()
                .name("Sala OSLO")
                .identifier("1.01")
                .level(1)
                .availability(true)
                .numberOfSeats(20)
                .organisationId(1L)
                .build();
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/conferencerooms").contentType(MediaType.APPLICATION_JSON).content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",equalTo(request.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void givenCreateInvalidConferenceRoomRequest_whenRegisterConferenceRoom_thenRegisterConferenceRoomAndReturnCreated() throws Exception {
        //given
        Organisation organisation = new Organisation("PG-Projekt", Organisation.Status.ACTIVE);
        organisationRepository.save(organisation);

        CreateConferenceRoomRequest request = CreateConferenceRoomRequest.builder()
                .name("Sala OSLO")
                .identifier("1.01")
                .level(1)
                .availability(true)
                .numberOfSeats(20)
                .organisationId(1L)
                .build();
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/conferencerooms").contentType(MediaType.APPLICATION_JSON).content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",equalTo(request.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @AfterEach
    void tearDown(){
        conferenceRoomRepository.deleteAll();
        organisationRepository.deleteAll();
    }

}