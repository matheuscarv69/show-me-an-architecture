package src.infrastructure.model;

import org.junit.jupiter.api.Test;
import src.domain.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserModelTest {

    public static final long ID = 1L;
    public static final String USERNAME = "James Gosling";
    public static final String DOCUMENT = "38713541048";
    public static final String USER_EMAIL = "gosling@gmail.com";

    @Test
    void mustToConvertModelInDomain() {

        // cenary
        var expectedDomain = makeExpectedDomain();
        var model = makeUserModel();

        // action
        var convertedDomain = model.toDomain();

        // validation
        assertEquals(expectedDomain, convertedDomain);

    }

    @Test
    void mustToCreateUserWithDefaultConstructor() {

        // cenary
        var expectedModel = new UserModel();
        var userWithDefaultConstructor = new UserModel();

        // validation
        assertEquals(expectedModel, userWithDefaultConstructor);

    }

    private User makeExpectedDomain() {

        return User
                .builder()
                .id(ID)
                .name(USERNAME)
                .document(DOCUMENT)
                .email(USER_EMAIL)
                .build();

    }

    private UserModel makeUserModel() {

        return UserModel
                .builder()
                .id(ID)
                .name(USERNAME)
                .document(DOCUMENT)
                .email(USER_EMAIL)
                .build();

    }

}