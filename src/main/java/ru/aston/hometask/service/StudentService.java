package ru.aston.hometask.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import ru.aston.hometask.entity.Book;
import ru.aston.hometask.entity.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class StudentService {
    private static final TypeReference<List<Student>> STUDENT_LIST_TYPE_REFERENCE = new TypeReference<>() {};
    private final ObjectMapper objectMapper;

    public StudentService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void exploreStudentList(List<Student> students, @NonNull Year afterYear, Long booksLimit) {
        students.stream()
                .peek(System.out::println)
                .map(Student::getBooks)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparingInt(Book::getNumberOfPages))
                .distinct()
                .filter(book -> book.isPublishedAfterYear(afterYear))
                .limit(booksLimit)
                .map(Book::getPublicationYear)
                .findAny()
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.printf("No books with published after %s year.%n", afterYear)
                );
    }

    public List<Student> getStudentListFromPath(Path path) throws IOException {
        String jsonString = Files.readString(path);
        if (jsonString.isBlank()) {
            return new ArrayList<>();
        }

        return objectMapper.readValue(jsonString, STUDENT_LIST_TYPE_REFERENCE);
    }
}
