package pl.migibud.conferenceroomreservationsystem.organisation;

import org.springframework.data.jpa.repository.JpaRepository;

interface SqlOrganisationRepository extends OrganisationRepository, JpaRepository<Organisation,Long> {
}
