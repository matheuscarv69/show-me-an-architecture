package src.application.controller.request;

import lombok.Getter;
import src.domain.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class UserRequest extends AbstractRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 11, max = 11)
    private String document;

    @NotBlank
    @Email
    private String email;

    @Override
    public User toDomain() {
        return User
                .builder()
                .name(name)
                .document(document)
                .email(email)
                .build();
    }

}
