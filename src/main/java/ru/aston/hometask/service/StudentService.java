package ru.aston.hometask.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import ru.aston.hometask.entity.Book;
import ru.aston.hometask.entity.Student;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class StudentService {
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
                        () -> System.out.printf("Книги с датой выпуска после %s года отсутствует.%n", afterYear)
                );
    }

    public List<Student> getStudentListFromPath(String path) throws IOException {
        return objectMapper.readValue(new File(path), new TypeReference<List<Student>>() {
        });
    }

}
