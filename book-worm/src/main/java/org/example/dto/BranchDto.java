package org.example.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BranchDto {
    private int id;
    private String name;
    private String address;
    private String contactNo;
}
