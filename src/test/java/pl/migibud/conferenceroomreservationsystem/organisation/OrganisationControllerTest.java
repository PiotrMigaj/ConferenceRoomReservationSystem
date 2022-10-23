package pl.migibud.conferenceroomreservationsystem.organisation;

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

import java.util.Optional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static pl.migibud.conferenceroomreservationsystem.utils.JsonParserUtil.asJsonString;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrganisationController.class)
class OrganisationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganisationFacade organisationFacade;

    @MockBean
    OrganisationRepository organisationRepository;

    @Test
    void givenInvalidOrganisationName_whenGetOrganisationByName_thenStatusIsNotFound() throws Exception {
        //given
        String message = OrganisationError.ORGANISATION_NOT_FOUND.getMessage();
        when(organisationRepository.findByNameAndAndStatus(anyString(),any())).thenReturn(Optional.empty());
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organisations/wodkan"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message[0]",equalTo(message)));
    }

    @Test
    void givenValidOrganisationName_whenGetOrganisationByName_thenOrganisationShouldBeReturned() throws Exception {
        //given
        when(organisationRepository.findByNameAndAndStatus(anyString(),any())).thenReturn(Optional.of(new Organisation(1L,"WODKAN", Organisation.Status.ACTIVE)));
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/organisations/wodkan"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",equalTo("WODKAN")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status",equalTo("ACTIVE")));
    }

    @Test
    void givenToShortOrganisationName_whenRegisterOrganisation_returnBadRequest() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/organisations").contentType(MediaType.APPLICATION_JSON).content(asJsonString(new Organisation("W"))))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verifyNoInteractions(organisationFacade);
    }

    @Test
    void givenToLongOrganisationName_whenRegisterOrganisation_returnBadRequest() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/organisations").contentType(MediaType.APPLICATION_JSON).content(asJsonString(new Organisation("W".repeat(21)))))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]",equalTo("{organisation.name.size.too_long}")));

        Mockito.verifyNoInteractions(organisationFacade);
    }

    @Test
    void givenValidOrganisation_whenRegisterOrganisation_thenReturnSavedOrganisation() throws Exception {
        //given
        when(organisationFacade.registerOrganisation(any())).thenReturn(new Organisation(1L,"WODKAN", Organisation.Status.ACTIVE));
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/organisations").contentType(MediaType.APPLICATION_JSON).content(asJsonString(new Organisation("WODKAN"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",equalTo("WODKAN")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status",equalTo("ACTIVE")))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}