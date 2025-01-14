package models;

import lombok.Data;

@Data
public class UpdateUserModel {
    private String name;
    private String job;
    private String updatedAt;
}