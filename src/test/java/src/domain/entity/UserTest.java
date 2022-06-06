package src.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    public static final long ID = 1L;
    public static final String USERNAME = "James Gosling";
    public static final String DOCUMENT = "38713541048";
    public static final String USER_EMAIL = "gosling@gmail.com";

    @Test
    void mustToCreateAnUser() {

        // cenary
        var expectedUser = makeUser();

        User userDomain = User
                .builder()
                .id(ID)
                .name(USERNAME)
                .document(DOCUMENT)
                .email(USER_EMAIL)
                .build();

        // validation
        assertEquals(expectedUser, userDomain);
        assertEquals(expectedUser.toString(), userDomain.toString());

    }


    private User makeUser() {

        return User
                .builder()
                .id(ID)
                .name(USERNAME)
                .document(DOCUMENT)
                .email(USER_EMAIL)
                .build();

    }
}