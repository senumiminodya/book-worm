package org.example.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.awt.*;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BorrowBookTm {
    private int id;
    private String bookTitle;
    private String author;
    private String genre;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private Button button;

}
