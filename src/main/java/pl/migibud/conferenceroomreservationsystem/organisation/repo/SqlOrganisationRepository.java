package pl.migibud.conferenceroomreservationsystem.organisation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.migibud.conferenceroomreservationsystem.organisation.Organisation;

interface SqlOrganisationRepository extends OrganisationRepository, JpaRepository<Organisation,Long> {
}
