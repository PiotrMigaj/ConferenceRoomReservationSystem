package pl.migibud.conferenceroomreservationsystem.organisation;

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
import pl.migibud.conferenceroomreservationsystem.exception.organisation.OrganisationError;

import static org.hamcrest.core.IsEqual.equalTo;
import static pl.migibud.conferenceroomreservationsystem.utils.JsonParserUtil.asJsonString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = ConferenceroomreservationsystemApplication.class)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class OrganisationIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OrganisationRepository organisationRepository;

    @Test
    void givenOrganisation_whenRegisterOrganisation_thenReturnSavedOrganisation() throws Exception {
        //given
        Organisation organisationToRegister = new Organisation("WODKAN");
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/organisations").contentType(MediaType.APPLICATION_JSON).content(asJsonString(organisationToRegister)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",equalTo("WODKAN")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status",equalTo("ACTIVE")))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void givenDuplicatedOrganisation_whenRegisterOrganisation_thenReturnBadRequest() throws Exception {
        //given
        Organisation organisationToRegister = new Organisation("WODKAN", Organisation.Status.ACTIVE);
        organisationRepository.save(organisationToRegister);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/organisations").contentType(MediaType.APPLICATION_JSON).content(asJsonString(organisationToRegister)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message[0]",equalTo(OrganisationError.ORGANISATION_ALREADY_EXISTS.getMessage())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void givenOrganisationToUpdate_whenOrganisationWithUpdateNameExists_thenReturnBadRequest() throws Exception {
        //given
        Organisation organisation1 = new Organisation("WODKAN", Organisation.Status.ACTIVE);
        Organisation organisation2 = new Organisation("WODKAN_2", Organisation.Status.ACTIVE);
        organisationRepository.save(organisation1);
        organisationRepository.save(organisation2);

        Organisation organisationToUpdate = new Organisation("WODKAN_2");
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/organisations/"+organisation1.getId()).contentType(MediaType.APPLICATION_JSON).content(asJsonString(organisationToUpdate)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message[0]",equalTo(OrganisationError.ORGANISATION_ALREADY_EXISTS.getMessage())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void givenIdOfOrganisation_whenDeleteOfOrganisation_thenReturnAccepted() throws Exception {
        //given
        Organisation organisation = new Organisation("WODKAN", Organisation.Status.ACTIVE);
        organisationRepository.save(organisation);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/organisations/1"))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @AfterEach
    void tearDown(){
        organisationRepository.deleteAll();
    }
}
