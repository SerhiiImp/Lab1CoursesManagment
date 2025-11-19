package ua.university;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import ua.university.exception.InvalidDataException;

public class GenericRepository<T extends Comparable<T>> {
    private static final Logger logger = Logger.getLogger(GenericRepository.class.getName());
    protected final List<T> storage = new ArrayList<>();
    private final IdentityExtractor<T> extractor;

    public GenericRepository(IdentityExtractor<T> extractor) {
        this.extractor = Objects.requireNonNull(extractor);
    }

    public void add(T element) {
        Objects.requireNonNull(element, "Element cannot be null");
        
        try {
            storage.add(element);
            logger.info(() -> "Successfully added element with id=" + extractor.extractId(element));
        } catch (InvalidDataException e) {
            logger.severe(() -> "Validation failed when adding element: " + e.getMessage());
            throw e;
        }
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

    public void sortByIdentity(String order) {
        if ("asc".equalsIgnoreCase(order)) {
            Collections.sort(storage);
            logger.info("Sorted ascending by natural order");
        } else if ("desc".equalsIgnoreCase(order)) {
            storage.sort(Collections.reverseOrder());
            logger.info("Sorted descending by natural order");
        } else {
            throw new IllegalArgumentException("Order must be 'asc' or 'desc'");
        }
    }

    public void sortBy(Comparator<T> comparator) {
        storage.sort(comparator);
        logger.info("Sorted by custom comparator");
    }
}
