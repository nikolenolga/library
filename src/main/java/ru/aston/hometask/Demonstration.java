package ru.aston.hometask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.aston.hometask.entity.Student;
import ru.aston.hometask.service.StudentService;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.util.List;
import java.util.Objects;

public class Demonstration {
    public static final Path ROOT = Paths.get(URI.create(
            Objects.requireNonNull(
                    Demonstration.class.getResource("/")
            ).toString()));

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        StudentService studentService = new StudentService(objectMapper);
        Path path = ROOT.resolve("students.json");

        List<Student> students = studentService.getStudentListFromPath(path);
        studentService.exploreStudentList(students, Year.of(2000), 3L);
    }
}
