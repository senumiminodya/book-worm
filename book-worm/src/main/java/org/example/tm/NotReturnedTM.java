package org.example.tm;

import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class NotReturnedTM {
    private int recordId;
    private String title;
    private String author;
    private String genre;
    private LocalDate borrowDate;
    private Button button;
    private int bookId;
}
