package pl.migibud.conferenceroomreservationsystem.organisation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryOrganisationRepositoryImpl implements OrganisationRepository {
    @Override
    public List<Organisation> findAllByStatus(Sort sort, Organisation.Status status) {
        throw new UnsupportedOperationException();
    }

    private final Map<Long, Organisation> organisationInMemDb = new ConcurrentHashMap<>();

    @Override
    public Optional<Organisation> findById(Long id) {
        return organisationInMemDb.entrySet().stream()
                .filter(v->v.getKey().equals(id))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    @Override
    public Optional<Organisation> findByName(String name) {
        return organisationInMemDb.entrySet().stream()
                .filter(v->v.getValue().getName().equals(name))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    @Override
    public Organisation save(Organisation organisation) {
        long id = organisationInMemDb.size();
        organisation.setId(++id);
        organisationInMemDb.put(id,organisation);
        return organisationInMemDb.get(id);
    }

    @Override
    public Optional<Organisation> findByIdAndAndStatus(Long id, Organisation.Status status) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Organisation> findByNameAndAndStatus(String name, Organisation.Status status) {
        throw new UnsupportedOperationException();
    }



    @Override
    public Page<Organisation> findAllByStatus(Pageable pageable, Organisation.Status status) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }
}
