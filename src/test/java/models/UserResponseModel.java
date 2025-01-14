package models;

import lombok.Data;

@Data
public class UserResponseModel {
    private UserByIdModel data;
    private Support support;  // Добавляем поле для "support"
}

@Data
class Support {
    private String url;
    private String text;
}