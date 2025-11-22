package ua.university;

import org.testng.Assert;
import org.testng.annotations.Test;
import ua.university.service.ConcurrentLoader;
import ua.university.service.DataProcessingService;

import java.time.LocalDate;

public class ConcurrencyTest {

    @Test
    public void testParallelLoadingAndProcessing() {
        ConcurrentLoader loader = new ConcurrentLoader();
        StudentRepository students = loader.loadStudentsFromJsonAsync("data/students.json").join();
        CourseRepository courses = loader.loadCoursesFromJsonAsync("data/courses.json").join();

        Assert.assertTrue(students.getAll().size() > 0);
        Assert.assertTrue(courses.getAll().size() > 0);

        DataProcessingService processing = new DataProcessingService();
        long ps = processing.countAdvancedCoursesParallelStream(courses);
        try {
            long ex = processing.countAdvancedCoursesExecutor(courses);
            Assert.assertEquals(ps, ex);
        } catch (Exception e) {
            Assert.fail("Executor processing failed: " + e.getMessage());
        }

        var date = LocalDate.of(2024,9,2);
        var a = processing.filterStudentsAfterParallelStream(students, date);
        var b = processing.filterStudentsAfterAsync(students, date).join();
        Assert.assertEquals(a.size(), b.size());

        loader.shutdown();
        processing.shutdown();
    }
}
