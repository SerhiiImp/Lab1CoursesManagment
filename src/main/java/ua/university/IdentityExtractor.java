package ua.university;

@FunctionalInterface
public interface IdentityExtractor<T> {
    Object extractId(T t);
}
