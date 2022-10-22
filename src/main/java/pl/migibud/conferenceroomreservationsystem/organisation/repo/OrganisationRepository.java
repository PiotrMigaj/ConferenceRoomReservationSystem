package pl.migibud.conferenceroomreservationsystem.organisation.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.migibud.conferenceroomreservationsystem.organisation.Organisation;

import java.util.List;
import java.util.Optional;

public interface OrganisationRepository {
    Optional<Organisation> findById(Long id);
    Optional<Organisation> findByName(String name);
    Organisation save(Organisation organisation);
    Optional<Organisation> findByIdAndAndStatus(Long id,Organisation.Status status);
    List<Organisation> findAllByStatus(Organisation.Status status);
    Page<Organisation> findAllByStatus(Pageable pageable,Organisation.Status status);
}
