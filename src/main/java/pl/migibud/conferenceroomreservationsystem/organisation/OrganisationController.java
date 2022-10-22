package pl.migibud.conferenceroomreservationsystem.organisation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.migibud.conferenceroomreservationsystem.exception.organisation.OrganisationException;
import pl.migibud.conferenceroomreservationsystem.organisation.repo.OrganisationRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static pl.migibud.conferenceroomreservationsystem.exception.organisation.OrganisationError.ORGANISATION_NOT_FOUND;

@Slf4j
@RestController
@RequestMapping("/api/organisations")
@RequiredArgsConstructor
class OrganisationController {

    private final OrganisationFacade organisationFacade;
    private final OrganisationRepository organisationRepository;

    @GetMapping(params = {"!page","!size"})
    ResponseEntity<List<Organisation>> getAllOrganisations(
            @RequestParam(required = false,defaultValue = "ACTIVE") Organisation.Status status
    ){
        return ResponseEntity.ok(organisationRepository.findAllByStatus(status));
    }

    @GetMapping
    ResponseEntity<Page<Organisation>> getOrganisations(
            @RequestParam(required = false,defaultValue = "0") Integer page,
            @RequestParam(required = false,defaultValue = "10") Integer size,
            @RequestParam(required = false,defaultValue = "ACTIVE") Organisation.Status status
    ){
        Page<Organisation> result = organisationRepository.findAllByStatus(PageRequest.of(page, size),status);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    ResponseEntity<Organisation> getOrganisationById(@PathVariable Long id){
        Organisation result = organisationRepository.findByIdAndAndStatus(id, Organisation.Status.ACTIVE)
                .orElseThrow(() -> new OrganisationException(ORGANISATION_NOT_FOUND));
        return ResponseEntity.ok(result);
    }

    @PostMapping
    ResponseEntity<Organisation> registerOrganisation(@RequestBody @Valid Organisation organisationToSave){
        Organisation result = organisationFacade.registerOrganisation(organisationToSave);
        return ResponseEntity.created(URI.create("api/organisation"+result.getId())).body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteOrganisation(@PathVariable Long id){
        organisationFacade.deleteOrganisation(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Organisation> updateOrganisation(
            @PathVariable Long id,
            @RequestBody @Valid Organisation organisationToUpdate
    ){
        Organisation result = organisationFacade.updateOrganisation(id, organisationToUpdate);
        return ResponseEntity.ok(result);
    }




}
