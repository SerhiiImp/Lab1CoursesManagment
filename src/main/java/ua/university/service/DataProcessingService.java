package ua.university.service;

import ua.university.Course;
import ua.university.CourseLevel;
import ua.university.CourseRepository;
import ua.university.Student;
import ua.university.StudentRepository;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class DataProcessingService {
    private static final Logger logger = Logger.getLogger(DataProcessingService.class.getName());

    private final ExecutorService executor;

    public DataProcessingService() {
        this(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }

    public DataProcessingService(ExecutorService executor) {
        this.executor = executor;
    }

    public long countAdvancedCoursesParallelStream(CourseRepository repo) {
        Instant start = Instant.now();
        long count = repo.getAll().parallelStream().filter(c -> c.getLevel() == CourseLevel.ADVANCED).count();
        logger.info(() -> "countAdvancedCoursesParallelStream took=" + Duration.between(start, Instant.now()).toMillis() + " ms");
        return count;
    }

    public long countAdvancedCoursesExecutor(CourseRepository repo) throws InterruptedException, ExecutionException {
        List<Course> courses = repo.getAll();
        int n = Math.max(1, Runtime.getRuntime().availableProcessors());
        int chunk = Math.max(1, courses.size() / n);
        List<Callable<Long>> tasks = new ArrayList<>();
        for (int i = 0; i < courses.size(); i += chunk) {
            int from = i;
            int to = Math.min(i + chunk, courses.size());
            tasks.add(() -> courses.subList(from, to).stream().filter(c -> c.getLevel() == CourseLevel.ADVANCED).count());
        }
        Instant start = Instant.now();
        List<Future<Long>> futures = executor.invokeAll(tasks);
        long result = 0L;
        for (Future<Long> f : futures) result += f.get();
        logger.info(() -> "countAdvancedCoursesExecutor took=" + Duration.between(start, Instant.now()).toMillis() + " ms");
        return result;
    }

    public List<Student> filterStudentsAfterParallelStream(StudentRepository repo, LocalDate date) {
        Instant start = Instant.now();
        List<Student> out = repo.getAll().parallelStream().filter(s -> s.enrollmentDate().isAfter(date)).toList();
        logger.info(() -> "filterStudentsAfterParallelStream took=" + Duration.between(start, Instant.now()).toMillis() + " ms");
        return out;
    }

    public CompletableFuture<List<Student>> filterStudentsAfterAsync(StudentRepository repo, LocalDate date) {
        Instant start = Instant.now();
        return CompletableFuture.supplyAsync(() -> repo.getAll().stream().filter(s -> s.enrollmentDate().isAfter(date)).toList(), executor)
                .whenComplete((r, t) -> logger.info(() -> "filterStudentsAfterAsync took=" + Duration.between(start, Instant.now()).toMillis() + " ms"));
    }

    public CompletableFuture<Double> averageCreditsAsync(CourseRepository repo) {
        return CompletableFuture.supplyAsync(() -> repo.getAll().stream().mapToInt(Course::getCredits).average().orElse(0.0), executor);
    }

    public CompletableFuture<Long> countByLevelAsync(CourseRepository repo, CourseLevel level) {
        return CompletableFuture.supplyAsync(() -> repo.getAll().stream().filter(c -> c.getLevel() == level).count(), executor);
    }

    public void shutdown() {
        executor.shutdown();
    }
}
