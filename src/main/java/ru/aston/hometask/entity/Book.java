package ru.aston.hometask.entity;

import lombok.*;

import java.time.Year;
import java.util.Objects;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String title;
    private String author;
    private Year publicationYear;
    private Integer numberOfPages;

    public boolean isPublishedAfterYear(@NonNull Year year) {
        return Objects.nonNull(this.publicationYear) && this.publicationYear.isAfter(year);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;
        return Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(author);
        return result;
    }
}
