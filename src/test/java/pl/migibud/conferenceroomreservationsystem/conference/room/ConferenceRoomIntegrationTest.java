package pl.migibud.conferenceroomreservationsystem.conference.room;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import pl.migibud.conferenceroomreservationsystem.ConferenceroomreservationsystemApplication;
import pl.migibud.conferenceroomreservationsystem.organisation.OrganisationRepository;

import static org.junit.jupiter.api.Assertions.*;

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
    void given_when_then(){
        //given

        //when

        //then
    }

    @AfterEach
    void tearDown(){
        organisationRepository.deleteAll();

    }

}