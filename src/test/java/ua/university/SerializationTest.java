package ua.university;

import com.fasterxml.jackson.core.type.TypeReference;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.university.exception.DataSerializationException;
import ua.university.service.SerializationService;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SerializationTest {
    private SerializationService serializationService;
    private List<Student> testStudents;
    private List<Course> testCourses;

    @BeforeMethod
    public void setUp() {
        serializationService = new SerializationService();

        testStudents = new ArrayList<>();
        testStudents.add(new Student("John", "Doe", "john@test.com", LocalDate.of(2024, 9, 1)));
        testStudents.add(new Student("Jane", "Smith", "jane@test.com", LocalDate.of(2024, 9, 2)));

        testCourses = new ArrayList<>();
        testCourses.add(new Course("Java", "Java Programming", 3, LocalDate.of(2025, 1, 15), CourseLevel.BEGINNER));
        testCourses.add(new Course("Python", "Python Programming", 4, LocalDate.of(2025, 2, 1), CourseLevel.INTERMEDIATE));
    }

    @Test
    public void testStudentJsonSerialization() throws DataSerializationException {
        String testFile = "build/test_students.json";
        
        serializationService.saveToJson(testStudents, testFile);
        List<Student> loaded = serializationService.loadFromJson(testFile, new TypeReference<List<Student>>() {});
        
        Assert.assertEquals(loaded.size(), testStudents.size());
        Assert.assertEquals(loaded.get(0).firstName(), testStudents.get(0).firstName());
        Assert.assertEquals(loaded.get(0).email(), testStudents.get(0).email());
        
        new File(testFile).delete();
    }

    @Test
    public void testStudentYamlSerialization() throws DataSerializationException {
        String testFile = "build/test_students.yaml";
        
        serializationService.saveToYaml(testStudents, testFile);
        List<Student> loaded = serializationService.loadFromYaml(testFile, new TypeReference<List<Student>>() {});
        
        Assert.assertEquals(loaded.size(), testStudents.size());
        Assert.assertEquals(loaded.get(1).lastName(), testStudents.get(1).lastName());
        Assert.assertEquals(loaded.get(1).enrollmentDate(), testStudents.get(1).enrollmentDate());
        
        new File(testFile).delete();
    }

    @Test
    public void testCourseJsonSerialization() throws DataSerializationException {
        String testFile = "build/test_courses.json";
        
        serializationService.saveToJson(testCourses, testFile);
        List<Course> loaded = serializationService.loadFromJson(testFile, new TypeReference<List<Course>>() {});
        
        Assert.assertEquals(loaded.size(), testCourses.size());
        Assert.assertEquals(loaded.get(0).getTitle(), testCourses.get(0).getTitle());
        Assert.assertEquals(loaded.get(0).getCredits(), testCourses.get(0).getCredits());
        
        new File(testFile).delete();
    }

    @Test
    public void testCourseYamlSerialization() throws DataSerializationException {
        String testFile = "build/test_courses.yaml";
        
        serializationService.saveToYaml(testCourses, testFile);
        List<Course> loaded = serializationService.loadFromYaml(testFile, new TypeReference<List<Course>>() {});
        
        Assert.assertEquals(loaded.size(), testCourses.size());
        Assert.assertEquals(loaded.get(1).getDescription(), testCourses.get(1).getDescription());
        Assert.assertEquals(loaded.get(1).getLevel(), testCourses.get(1).getLevel());
        
        new File(testFile).delete();
    }

    @Test(expectedExceptions = DataSerializationException.class)
    public void testLoadFromNonExistentFile() throws DataSerializationException {
        serializationService.loadFromJson("nonexistent_file.json", new TypeReference<List<Student>>() {});
    }

    @Test
    public void testDataIntegrity() throws DataSerializationException {
        String jsonFile = "build/integrity_test.json";
        String yamlFile = "build/integrity_test.yaml";
        
        serializationService.saveToJson(testStudents, jsonFile);
        serializationService.saveToYaml(testStudents, yamlFile);
        
        List<Student> fromJson = serializationService.loadFromJson(jsonFile, new TypeReference<List<Student>>() {});
        List<Student> fromYaml = serializationService.loadFromYaml(yamlFile, new TypeReference<List<Student>>() {});
        
        Assert.assertEquals(fromJson.size(), fromYaml.size());
        for (int i = 0; i < fromJson.size(); i++) {
            Assert.assertEquals(fromJson.get(i).firstName(), fromYaml.get(i).firstName());
            Assert.assertEquals(fromJson.get(i).lastName(), fromYaml.get(i).lastName());
            Assert.assertEquals(fromJson.get(i).email(), fromYaml.get(i).email());
            Assert.assertEquals(fromJson.get(i).enrollmentDate(), fromYaml.get(i).enrollmentDate());
        }
        
        new File(jsonFile).delete();
        new File(yamlFile).delete();
    }
}
