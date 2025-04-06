package com.example.databaseService.mapper;

import com.example.databaseService.entity.User;

public class UserMapper {
    public static User toUser(String input) {
        User user = new User();

        try {
            input = input.replace("User(", "").replace(")", "");
            String[] fields = input.split(", ");

            for (String field : fields) {
                String[] keyValue = field.split("=");
                String key = keyValue[0];
                String value = keyValue.length > 1 ? keyValue[1] : "";

                switch (key) {
                    case "id":
                        user.setId(value);
                        break;
                    case "username":
                        user.setUsername(value);
                        break;
                    case "password":
                        user.setPassword(value);
                        break;
                    case "role":
                        user.setRole(value);
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return user;
    }
}
