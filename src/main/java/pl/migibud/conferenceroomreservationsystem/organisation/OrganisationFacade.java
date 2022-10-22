package pl.migibud.conferenceroomreservationsystem.organisation;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import pl.migibud.conferenceroomreservationsystem.exception.organisation.OrganisationException;
import pl.migibud.conferenceroomreservationsystem.organisation.repo.OrganisationRepository;

import static pl.migibud.conferenceroomreservationsystem.exception.organisation.OrganisationError.ORGANISATION_ALREADY_EXISTS;
import static pl.migibud.conferenceroomreservationsystem.exception.organisation.OrganisationError.ORGANISATION_NOT_FOUND;

@RequiredArgsConstructor
public class OrganisationFacade {

    private final OrganisationRepository organisationRepository;

    @Transactional
    public Organisation registerOrganisation(Organisation organisationToSave){
        return organisationRepository.findByName(organisationToSave.getName())
                .map(organisation -> {
                    if (Organisation.Status.ACTIVE.equals(organisation.getStatus())){
                        throw new OrganisationException(ORGANISATION_ALREADY_EXISTS);
                    }else {
                        organisation.setStatus(Organisation.Status.ACTIVE);
                        return organisation;
                    }
                }).orElseGet(()->{
                    organisationToSave.setStatus(Organisation.Status.ACTIVE);
                    return organisationRepository.save(organisationToSave);
                });
    }

    @Transactional
    public void deleteOrganisation(@PathVariable Long id){
        Organisation organisation = organisationRepository.findById(id)
                .orElseThrow(() -> new OrganisationException(ORGANISATION_NOT_FOUND));
        organisation.setStatus(Organisation.Status.INACTIVE);
    }

    @Transactional
    public Organisation updateOrganisation(Long organisationId,Organisation organisationToUpdate){
        Organisation organisation = organisationRepository.findByIdAndAndStatus(organisationId, Organisation.Status.ACTIVE)
                .orElseThrow(() -> new OrganisationException(ORGANISATION_NOT_FOUND));
        organisationRepository.findByName(organisationToUpdate.getName())
                .ifPresent(
                        o->{
                            throw new OrganisationException(ORGANISATION_ALREADY_EXISTS);
                        });
        organisation.setName(organisationToUpdate.getName());
        return organisation;
    }
}
