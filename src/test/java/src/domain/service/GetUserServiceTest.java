package src.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import src.domain.entity.User;
import src.domain.exceptions.UserNotFoundException;
import src.domain.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserServiceTest {

    public static final long ID = 1L;
    public static final String USERNAME = "James Gosling";
    public static final String DOCUMENT = "38713541048";
    public static final String USER_EMAIL = "gosling@gmail.com";

    @InjectMocks
    GetUserService service;

    @Mock
    UserRepository repository;


    @Test
    void mustToGetAnUserById() {

        // cenary
        var userId = ID;
        var expectedUser = makeUser();

        when(repository.getUserById(userId)).thenReturn(expectedUser);

        // action
        var recoveredUser = service.getUserById(userId);

        // validation
        assertEquals(expectedUser, recoveredUser);

    }

    @Test
    void dontShouldToGetAnUserWhenIdNotFound() {

        // cenary
        var userId = 35L;

        when(repository.getUserById(userId)).thenThrow(UserNotFoundException.class);

        // action
        var exception = assertThrows(
                UserNotFoundException.class, () -> service.getUserById(userId)
        );

        // validation
        assertEquals(UserNotFoundException.class, exception.getClass());

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