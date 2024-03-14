package org.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookDto {
    private int id;
    private String title;
    private String author;
    private String genre;
    private String availability;
    private String branchName;
    private int branchId;
}
