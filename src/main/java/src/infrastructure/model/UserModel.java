package src.infrastructure.model;

import lombok.*;
import src.domain.entity.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "USER")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModel extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String document;
    private String email;

    public UserModel(User user) {
        name = user.getName();
        document = user.getDocument();
        email = user.getEmail();
    }

    @Override
    public User toDomain() {
        return User
                .builder()
                .id(id)
                .name(name)
                .document(document)
                .email(email)
                .build();
    }

}
