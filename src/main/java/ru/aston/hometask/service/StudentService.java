package ru.aston.hometask.service;

import lombok.NonNull;
import ru.aston.hometask.entity.Book;
import ru.aston.hometask.entity.Student;

import java.time.Year;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class StudentService {
    public void exploreStudentList(List<Student> students, @NonNull Year afterYear) {
        students.stream()
                .peek(System.out::println)
                .map(Student::getBooks)
                .flatMap(Collection::stream)
                .sorted(Comparator.comparingInt(Book::getNumberOfPages))
                .distinct()
                .filter(book -> book.isPublishedAfterYear(afterYear))
                .limit(3L)
                .map(Book::getPublicationYear)
                .findAny()
                .ifPresentOrElse(System.out::println, () -> System.out.printf("Книга датой выпуска после %s года отсутствует.%n", afterYear));
    }

}
