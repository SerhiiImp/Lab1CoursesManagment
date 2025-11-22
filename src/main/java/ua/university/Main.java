package ua.university;

import ua.university.config.ConfigManager;
import ua.university.service.ConcurrentLoader;
import ua.university.service.DataProcessingService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Starting Lab 9 - Multithreading & Concurrency");

        try {
            ConfigManager config = new ConfigManager("src/main/resources/config.properties");
            String studentsJsonPath = config.getProperty("students.json.path");
            String studentsYamlPath = config.getProperty("students.yaml.path");
            String coursesJsonPath = config.getProperty("courses.json.path");
            String coursesYamlPath = config.getProperty("courses.yaml.path");

            ConcurrentLoader loader = new ConcurrentLoader();

            CompletableFuture<StudentRepository> studentsFromJsonF = loader.loadStudentsFromJsonAsync(studentsJsonPath);
            CompletableFuture<StudentRepository> studentsFromYamlF = loader.loadStudentsFromYamlAsync(studentsYamlPath);
            CompletableFuture<CourseRepository> coursesFromJsonF = loader.loadCoursesFromJsonAsync(coursesJsonPath);
            CompletableFuture<CourseRepository> coursesFromYamlF = loader.loadCoursesFromYamlAsync(coursesYamlPath);

            StudentRepository studentsFromJson = studentsFromJsonF.join();
            StudentRepository studentsFromYaml = studentsFromYamlF.join();
            CourseRepository coursesFromJson = coursesFromJsonF.join();
            CourseRepository coursesFromYaml = coursesFromYamlF.join();

            logger.info(() -> "Loaded students JSON=" + studentsFromJson.getAll().size() + ", YAML=" + studentsFromYaml.getAll().size());
            logger.info(() -> "Loaded courses JSON=" + coursesFromJson.getAll().size() + ", YAML=" + coursesFromYaml.getAll().size());

            DataProcessingService processing = new DataProcessingService();

            long advPs = processing.countAdvancedCoursesParallelStream(coursesFromJson);
            long advExec;
            try {
                advExec = processing.countAdvancedCoursesExecutor(coursesFromJson);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            logger.info(() -> "Advanced courses: parallelStream=" + advPs + ", executor=" + advExec);

            var afterDate = LocalDate.of(2024, 9, 2);
            var s1 = processing.filterStudentsAfterParallelStream(studentsFromJson, afterDate);
            var s2 = processing.filterStudentsAfterAsync(studentsFromJson, afterDate).join();
            logger.info(() -> "Filtered students count: parallelStream=" + s1.size() + ", completableFuture=" + s2.size());

            var avg = processing.averageCreditsAsync(coursesFromJson).thenCombine(
                    processing.countByLevelAsync(coursesFromJson, CourseLevel.ADVANCED),
                    (a, c) -> {
                        logger.info(() -> "Average credits=" + a + ", advanced count=" + c);
                        return a;
                    }
            ).join();
            logger.info(() -> "Combined result average credits=" + avg);

            loader.shutdown();
            processing.shutdown();

            logger.info("Lab 9 demo completed");

        } catch (IOException e) {
            logger.severe(() -> "Failed to load configuration: " + e.getMessage());
        }
    }
}
