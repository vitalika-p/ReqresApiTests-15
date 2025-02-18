package models;

import lombok.Data;

@Data
public class UserResponseModel {
    private UserByIdModel data;
    private SupportModel support;
}
