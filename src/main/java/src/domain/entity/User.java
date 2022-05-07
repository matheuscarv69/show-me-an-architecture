package src.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {

    private Long id;
    private String name;
    private String document;
    private String email;

}
