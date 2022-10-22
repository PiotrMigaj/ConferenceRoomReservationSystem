package pl.migibud.conferenceroomreservationsystem.organisation.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.migibud.conferenceroomreservationsystem.organisation.Organisation;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryOrganisationRepositoryImpl implements OrganisationRepository{

    public static final Map<Long, Organisation> ORGANISATION_IN_MEM_DB = new ConcurrentHashMap<>();

    @Override
    public Optional<Organisation> findById(Long id) {
        return ORGANISATION_IN_MEM_DB.entrySet().stream()
                .filter(v->v.getKey().equals(id))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    @Override
    public Optional<Organisation> findByName(String name) {
        return ORGANISATION_IN_MEM_DB.entrySet().stream()
                .filter(v->v.getValue().getName().equals(name))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    @Override
    public Organisation save(Organisation organisation) {
        long id = ORGANISATION_IN_MEM_DB.size();
        organisation.setId(++id);
        ORGANISATION_IN_MEM_DB.put(id,organisation);
        return ORGANISATION_IN_MEM_DB.get(id);
    }

    @Override
    public Optional<Organisation> findByIdAndAndStatus(Long id, Organisation.Status status) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Organisation> findAllByStatus(Organisation.Status status) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<Organisation> findAllByStatus(Pageable pageable, Organisation.Status status) {
        throw new UnsupportedOperationException();
    }
}
