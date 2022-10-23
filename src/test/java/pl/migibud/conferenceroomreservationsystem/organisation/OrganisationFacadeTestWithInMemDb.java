package pl.migibud.conferenceroomreservationsystem.organisation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.migibud.conferenceroomreservationsystem.exception.organisation.OrganisationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrganisationFacadeTestWithInMemDb {

    private OrganisationFacade organisationFacade;

    @BeforeEach
    void setUp() {
        organisationFacade = new OrganisationFacade(new InMemoryOrganisationRepositoryImpl());
    }

    @Test
    void shouldRegisterOrganisationIfInputDataAreValid(){
        //given
        Organisation input = new Organisation("PG-Projekt");
        //when
        Organisation result = organisationFacade.registerOrganisation(input);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(Organisation.Status.ACTIVE);
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowExceptionIfOrganisationAlreadyExists(){
        //given
        Organisation input = new Organisation("PG-Projekt");
        organisationFacade.registerOrganisation(input);
        //then
        assertThatThrownBy(()->organisationFacade.registerOrganisation(input))
                .isNotNull()
                .isInstanceOf(OrganisationException.class);
    }
}