package ua.university;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

public class GenericRepository<T> {
    private static final Logger logger = Logger.getLogger(GenericRepository.class.getName());
    private final List<T> storage = new ArrayList<>();
    private final IdentityExtractor<T> extractor;

    public GenericRepository(IdentityExtractor<T> extractor) {
        this.extractor = Objects.requireNonNull(extractor);
    }

    public void add(T element) {
        Objects.requireNonNull(element);
        storage.add(element);
        logger.info(() -> "Added element with id=" + extractor.extractId(element));
    }

    public boolean removeByIdentity(Object id) {
        Optional<T> found = findByIdentity(id);
        if (found.isPresent()) {
            storage.remove(found.get());
            logger.info(() -> "Removed element with id=" + id);
            return true;
        }
        logger.warning(() -> "No element found to remove with id=" + id);
        return false;
    }

    public Optional<T> findByIdentity(Object id) {
        for (T t : storage) {
            Object eid = extractor.extractId(t);
            if (eid == null) continue;
            if (eid.equals(id)) return Optional.of(t);
        }
        return Optional.empty();
    }

    public List<T> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(storage));
    }
}
