package ru.aston.hometask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.aston.hometask.entity.Student;
import ru.aston.hometask.service.StudentService;

import java.io.IOException;
import java.time.Year;
import java.util.List;

public class Demonstration {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        StudentService studentService = new StudentService(objectMapper);

        String path = "src/main/resources/students.json";
        List<Student> students = studentService.getStudentListFromPath(path);
        studentService.exploreStudentList(students, Year.of(2000), 3L);
    }
}
