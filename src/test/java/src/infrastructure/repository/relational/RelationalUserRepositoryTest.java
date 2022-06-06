package src.infrastructure.repository.relational;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import src.domain.entity.User;
import src.domain.exceptions.UserNotFoundException;
import src.infrastructure.model.UserModel;
import src.infrastructure.repository.jpa.UserRepositoryJPA;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RelationalUserRepositoryTest {

    public static final long ID = 1L;
    public static final String USERNAME = "James Gosling";
    public static final String DOCUMENT = "38713541048";
    public static final String USER_EMAIL = "gosling@gmail.com";

    @InjectMocks
    RelationalUserRepository relationalRepository;

    @Mock
    UserRepositoryJPA repositoryJPA;

    @Test
    void mustToRegistryAnUser() {

        // cenary
        var inputUser = makeInputUser();
        var expectedUserDomain = makeUserDomain();
        var userModel = makeUserModel();

        ArgumentCaptor<UserModel> userCaptor = ArgumentCaptor.forClass(UserModel.class);

        when(repositoryJPA.save(userCaptor.capture())).thenReturn(userModel);

        // action
        var registeredUser = relationalRepository.registry(inputUser);

        // validation
        assertEquals(expectedUserDomain, registeredUser);

    }

    @Test
    void mustToGetAnUserById() {

        // cenary
        var userId = ID;
        var expectedUser = makeUserDomain();
        var userModel = makeUserModel();

        when(repositoryJPA.findById(userId)).thenReturn(Optional.of(userModel));

        // action
        var recoveredUser = relationalRepository.getUserById(userId);

        // validation
        assertEquals(expectedUser, recoveredUser);
    }

    @Test
    void dontShouldGetAnUserWhenIdIsInvalid() {

        // cenary
        var userId = 35L;

        when(repositoryJPA.findById(userId)).thenReturn(Optional.empty());

        // action
        var exception = assertThrows(
                UserNotFoundException.class, () -> relationalRepository.getUserById(userId)
        );

        // validation
        assertEquals(UserNotFoundException.class, exception.getClass());
    }

    private User makeInputUser() {

        return User
                .builder()
                .name(USERNAME)
                .document(DOCUMENT)
                .email(USER_EMAIL)
                .build();

    }

    private User makeUserDomain() {

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