package ua.university.service;

import com.fasterxml.jackson.core.type.TypeReference;
import ua.university.Course;
import ua.university.CourseRepository;
import ua.university.Student;
import ua.university.StudentRepository;
import ua.university.exception.DataSerializationException;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcurrentLoader {
    private static final Logger logger = Logger.getLogger(ConcurrentLoader.class.getName());

    private final SerializationService serializationService;
    private final ExecutorService executor;

    public ConcurrentLoader() {
        this(new SerializationService(), Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }

    public ConcurrentLoader(SerializationService serializationService, ExecutorService executor) {
        this.serializationService = Objects.requireNonNull(serializationService);
        this.executor = Objects.requireNonNull(executor);
    }

    public CompletableFuture<StudentRepository> loadStudentsFromJsonAsync(String path) {
        return CompletableFuture.supplyAsync(() -> {
            Instant start = Instant.now();
            try {
                List<Student> students = serializationService.loadFromJson(path, new TypeReference<List<Student>>(){});
                StudentRepository repo = new StudentRepository();
                students.parallelStream().forEach(repo::add);
                logger.info(() -> "Loaded students from JSON: " + path + ", count=" + repo.getAll().size()
                        + ", took=" + Duration.between(start, Instant.now()).toMillis() + " ms");
                return repo;
            } catch (DataSerializationException e) {
                logger.log(Level.SEVERE, "Failed to load students from JSON", e);
                throw new CompletionException(e);
            }
        }, executor);
    }

    public CompletableFuture<StudentRepository> loadStudentsFromYamlAsync(String path) {
        return CompletableFuture.supplyAsync(() -> {
            Instant start = Instant.now();
            try {
                List<Student> students = serializationService.loadFromYaml(path, new TypeReference<List<Student>>(){});
                StudentRepository repo = new StudentRepository();
                students.parallelStream().forEach(repo::add);
                logger.info(() -> "Loaded students from YAML: " + path + ", count=" + repo.getAll().size()
                        + ", took=" + Duration.between(start, Instant.now()).toMillis() + " ms");
                return repo;
            } catch (DataSerializationException e) {
                logger.log(Level.SEVERE, "Failed to load students from YAML", e);
                throw new CompletionException(e);
            }
        }, executor);
    }

    public CompletableFuture<CourseRepository> loadCoursesFromJsonAsync(String path) {
        return CompletableFuture.supplyAsync(() -> {
            Instant start = Instant.now();
            try {
                List<Course> courses = serializationService.loadFromJson(path, new TypeReference<List<Course>>(){});
                CourseRepository repo = new CourseRepository();
                courses.parallelStream().forEach(repo::add);
                logger.info(() -> "Loaded courses from JSON: " + path + ", count=" + repo.getAll().size()
                        + ", took=" + Duration.between(start, Instant.now()).toMillis() + " ms");
                return repo;
            } catch (DataSerializationException e) {
                logger.log(Level.SEVERE, "Failed to load courses from JSON", e);
                throw new CompletionException(e);
            }
        }, executor);
    }

    public CompletableFuture<CourseRepository> loadCoursesFromYamlAsync(String path) {
        return CompletableFuture.supplyAsync(() -> {
            Instant start = Instant.now();
            try {
                List<Course> courses = serializationService.loadFromYaml(path, new TypeReference<List<Course>>(){});
                CourseRepository repo = new CourseRepository();
                courses.parallelStream().forEach(repo::add);
                logger.info(() -> "Loaded courses from YAML: " + path + ", count=" + repo.getAll().size()
                        + ", took=" + Duration.between(start, Instant.now()).toMillis() + " ms");
                return repo;
            } catch (DataSerializationException e) {
                logger.log(Level.SEVERE, "Failed to load courses from YAML", e);
                throw new CompletionException(e);
            }
        }, executor);
    }

    public void shutdown() {
        executor.shutdown();
    }
}
