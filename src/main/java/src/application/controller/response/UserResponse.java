package src.application.controller.response;

import lombok.Getter;
import src.domain.entity.User;

@Getter
public class UserResponse {

    private Long id;
    private String name;
    private String document;
    private String email;

    public UserResponse(User user) {
        id = user.getId();
        name = user.getName();
        document = user.getDocument();
        email = user.getEmail();
    }
}
