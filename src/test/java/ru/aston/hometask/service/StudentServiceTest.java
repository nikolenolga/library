package ru.aston.hometask.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.aston.hometask.entity.Book;
import ru.aston.hometask.entity.Student;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class StudentServiceTest {
    public static final Path TEST_ROOT = Paths.get(URI.create(
            Objects.requireNonNull(
                    StudentServiceTest.class.getResource("/")
            ).toString()));
    private static final Path PATH = TEST_ROOT.resolve("test.json");
    private static final Path EMPTY_PATH = TEST_ROOT.resolve("empty_test.json");
    private static final Path WRONG_PATH = TEST_ROOT.resolve("wrong_test.json");
    private static StudentService studentService;

    @BeforeAll
    static void setUp() {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        studentService = new StudentService(objectMapper);
    }

    @Test
    void givenWrongJson_whenGetStudentListFromPath_thenThrowsDatabindException() {
        //when //then
        Assertions.assertThrows(JsonParseException.class, () -> studentService.getStudentListFromPath(WRONG_PATH));
    }

    @Test
    void givenEmptyJson_whenGetStudentListFromPath_thenReturnEmptyList() throws IOException {
        //when
        List<Student> students = studentService.getStudentListFromPath(EMPTY_PATH);
        //then
        Assertions.assertTrue(students.isEmpty());
    }

    @Test
    void givenJsonWithThreeStudents_whenGetStudentListFromPath_thenReturnStudentListWithThreeStudents() throws IOException {
        //given
        int expected = 3;
        //when
        List<Student> students = studentService.getStudentListFromPath(PATH);
        //then
        Assertions.assertEquals(3, students.size());
    }

    @Test
    void givenJsonWithStudentWithThreeBooks_whenGetStudentListFromPath_thenReturnStudentListWithStudentWithThreeBooks() throws IOException {
        //given
        int expected = 3;
        //when
        List<Student> students = studentService.getStudentListFromPath(PATH);
        //then
        List<Book> books = students.get(1).getBooks();
        Assertions.assertEquals(3, books.size());
    }

    @Test
    void givenJsonWithThreeStudent_whenGetStudentListFromPath_thenReturnStudentListContainingCurrentStudent() throws IOException {
        //given
        Student expected = new Student(3L, "Николай", "Николаев", new ArrayList<>());
        //when
        List<Student> students = studentService.getStudentListFromPath(PATH);
        //then
        Assertions.assertTrue(students.contains(expected));
    }

    @Test
    void givenJsonWithStudentWithoutBooks_whenGetStudentListFromPath_thenReturnStudentListWithStudentWithEmptyBookList() throws IOException {
        //given //when
        List<Student> students = studentService.getStudentListFromPath(PATH);
        //then
        List<Book> books = students.get(2).getBooks();
        Assertions.assertTrue(books.isEmpty());
    }
}