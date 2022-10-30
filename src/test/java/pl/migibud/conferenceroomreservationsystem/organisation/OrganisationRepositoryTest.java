package pl.migibud.conferenceroomreservationsystem.organisation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class OrganisationRepositoryTest {

    @Autowired
    OrganisationRepository organisationRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @ParameterizedTest
    @ArgumentsSource(FindByIdArgumentProvider.class)
    void givenListOfOrganisations_whenFindById_thenOrganisationShouldBeReturned(
            List<Organisation> organisations,
            Long id,
            boolean expected
    ){
        //given
        organisations.forEach(organisation -> testEntityManager.persist(organisation));
        //when
        Optional<Organisation> result = organisationRepository.findById(id);
        //then
        assertThat(result.isPresent()).isEqualTo(expected);
    }

    @ParameterizedTest
    @ArgumentsSource(FindByNameArgumentProvider.class)
    void givenListOfOrganisations_whenFindByName_thenOrganisationShouldBeReturned(
            List<Organisation> organisations,
            String name,
            boolean expected
    ){
        //given
        organisations.forEach(organisation -> testEntityManager.persist(organisation));
        //when
        Optional<Organisation> result = organisationRepository.findByName(name);
        //then
        assertThat(result.isPresent()).isEqualTo(expected);
    }

    @Test
    void givenNewOrganisation_whenSaveObject_thenShouldSaveInDb(){
        //given
        Organisation input = new Organisation("PG-Projekt");
        //when
        Organisation result = organisationRepository.save(input);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo(input.getName());
    }

    @ParameterizedTest
    @ArgumentsSource(FindAllByStatusProvider.class)
    void givenListOfOrganisations_whenFindAllByStatus_thenReturnListOfOrganisationsFromDb(
            List<Organisation> given,
            Sort sort,
            Organisation.Status status,
            List<Organisation> expected
    ){
        //given
        given.forEach(organisation -> testEntityManager.persist(organisation));
        //when
        List<Organisation> result = organisationRepository.findAllByStatus(sort, status);
        //then
        IntStream.range(0,result.size()).forEach(i-> assertThat(result.get(i).getStatus()).isEqualTo(expected.get(i).getStatus()));
        IntStream.range(0,result.size()).forEach(i-> assertThat(result.get(i).getName()).isEqualTo(expected.get(i).getName()));
    }

    @ParameterizedTest
    @ArgumentsSource(FindByIdAndStatusArgumentProvider.class)
    void givenListOfOrganisations_whenFindByIdAndStatus_thenReturnOrganisationFromDb(
            List<Organisation> organisations,
            Long id,
            Organisation.Status status,
            boolean expected
    ){
        //given
            organisations.forEach(organisation -> testEntityManager.persist(organisation));
        //when
        Optional<Organisation> result = organisationRepository.findByIdAndAndStatus(id, status);
        //then
        assertThat(result.isPresent()).isEqualTo(expected);
    }






}