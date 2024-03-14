package org.example.tm;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HistoryTM {
    private int bookId;
    private String title;
    private String author;
    private Boolean isReturned;
    private LocalDate borrowDate;
    private LocalDate dueDate;
}
