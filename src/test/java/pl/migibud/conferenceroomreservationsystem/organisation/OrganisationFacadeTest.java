package pl.migibud.conferenceroomreservationsystem.organisation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.migibud.conferenceroomreservationsystem.exception.organisation.OrganisationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class OrganisationFacadeTest {

    @TestConfiguration
    static class OrganisationServiceTestConfiguration{
        @Bean
        OrganisationFacade organisationFacade(OrganisationRepository organisationRepository){
            return new OrganisationFacade(organisationRepository);
        }
    }

    @MockBean
    OrganisationRepository organisationRepository;

    @Autowired
    OrganisationFacade organisationFacade;

    @Test
    void whenOrganisationAlreadyExists_thenRegisterOrganisation_shouldThrowException(){
        //given
        Organisation input = new Organisation("PG-Projekt", Organisation.Status.ACTIVE);
        when(organisationRepository.findByName(anyString())).thenReturn(Optional.of(input));
        //when
        //then
        assertThatThrownBy(()->organisationFacade.registerOrganisation(input))
                .isNotNull()
                .isInstanceOf(OrganisationException.class);
    }

    @Test
    void whenOrganisationStatusIsInactive_thenRegisterOrganisation_shouldToggleStatusToActive(){
        //given
        String name = "PG-Projekt";
        Organisation input = new Organisation(name);
        when(organisationRepository.findByName(anyString())).thenReturn(Optional.of(new Organisation(name, Organisation.Status.INACTIVE)));
        //when
        Organisation result = organisationFacade.registerOrganisation(input);
        //then
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getStatus()).isEqualTo(Organisation.Status.ACTIVE);
    }

    @Test
    void whenOrganisationIsNotRegister_thenRegisterOrganisation_shouldRegisterOrganisation(){
        //given
        String name = "PG-Projekt";
        Organisation input = new Organisation(name);
        when(organisationRepository.findByName(anyString())).thenReturn(Optional.empty());
        ArgumentCaptor<Organisation> organisationArgumentCaptor = ArgumentCaptor.forClass(Organisation.class);
        //when
        Organisation result = organisationFacade.registerOrganisation(input);
        //then
        verify(organisationRepository,times(1)).save(organisationArgumentCaptor.capture());
        String resultNameOfCompany = organisationArgumentCaptor.getValue().getName();
        assertThat(resultNameOfCompany).isEqualTo(name);
    }

    @Test
    void whenOrganisationDoesNotExists_thenUpdateOrganisation_shouldThrowException(){
        //given
        String name = "PG-Projekt";
        Long id = 1L;
        Organisation organisation = new Organisation(name, Organisation.Status.ACTIVE);
        when(organisationRepository.findByIdAndAndStatus(anyLong(),any())).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(()->organisationFacade.updateOrganisation(id,organisation))
                .isNotNull()
                .isInstanceOf(OrganisationException.class);
    }

    @Test
    void whenOrganisationAlreadyExistsButUpdateNameIsNotValid_thenUpdateOrganisation_shouldThrowException(){
        //given
        Long id = 1L;
        Organisation organisationToUpdate = new Organisation("PG-Projekt");
        when(organisationRepository.findByIdAndAndStatus(id, Organisation.Status.ACTIVE)).thenReturn(Optional.of(new Organisation(id, "WODKAN", Organisation.Status.ACTIVE)));
        when(organisationRepository.findByName(organisationToUpdate.getName())).thenReturn(Optional.of(new Organisation(2L, organisationToUpdate.getName(), Organisation.Status.ACTIVE)));
        //when
        //then
        assertThatThrownBy(()->organisationFacade.updateOrganisation(id,organisationToUpdate))
                .isNotNull()
                .isInstanceOf(OrganisationException.class);
    }

    @Test
    void whenOrganisationToUpdateAlreadyExistsAndUpdateNameIsValid_thenUpdateOrganisation_shouldUpdateOrganisation(){
        //given
        Long id = 1L;
        Organisation organisationToUpdate = new Organisation("PG-Projekt");
        when(organisationRepository.findByIdAndAndStatus(id, Organisation.Status.ACTIVE)).thenReturn(Optional.of(new Organisation(id, "WODKAN", Organisation.Status.ACTIVE)));
        when(organisationRepository.findByName(anyString())).thenReturn(Optional.empty());
        //when
        Organisation result = organisationFacade.updateOrganisation(id, organisationToUpdate);
        //then
        assertThat(result.getName()).isEqualTo(organisationToUpdate.getName());
    }

    @Test
    void whenOrganisationDoesNotExists_thenDeleteOrganisation_shouldThrowException(){
        //given
        Long id=1L;
        when(organisationRepository.findById(id)).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(()->organisationFacade.deleteOrganisation(id))
                .isNotNull()
                .isInstanceOf(OrganisationException.class);
    }


}