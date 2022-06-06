package src.domain.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import src.application.adapters.CpfValidatorAdapter;
import src.domain.entity.User;
import src.domain.exceptions.InvalidUserDocumentException;
import src.domain.repository.UserRepository;
import src.infrastructure.agents.cpfvalidator.response.CheckCpfResponse;
import src.infrastructure.agents.cpfvalidator.response.CpfStatusEnum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistryUserServiceTest {

    public static final long ID = 1L;
    public static final String USERNAME = "James Gosling";
    public static final String DOCUMENT = "38713541048";
    public static final String USER_EMAIL = "gosling@gmail.com";

    @InjectMocks
    RegistryUserService service;

    @Mock
    CpfValidatorAdapter cpfValidatorAdapter;

    @Mock
    UserRepository repository;


    @Test
    void mustToRegistryAnUser() {

        // cenary
        var inputUser = makeInputUser();
        var expectedUser = makeUser();
        var cpfStatusAble = makeCpfStatus(CpfStatusEnum.ABLE_TO_VOTE);

        when(cpfValidatorAdapter.checkCPF(inputUser.getDocument())).thenReturn(cpfStatusAble);
        when(repository.registry(inputUser)).thenReturn(expectedUser);

        // action
        var registeredUser = service.registry(inputUser);

        // validation
        assertEquals(expectedUser, registeredUser);

    }

    @Test
    void dontShouldToRegistryAnUserWhenTheyIsUnableToVote() {

        // cenary
        var inputUser = makeInputUser();
        var cpfStatusUnable = makeCpfStatus(CpfStatusEnum.UNABLE_TO_VOTE);

        when(cpfValidatorAdapter.checkCPF(inputUser.getDocument())).thenReturn(cpfStatusUnable);

        // action
        var exception = assertThrows(
                InvalidUserDocumentException.class, () -> service.registry(inputUser)
        );

        // validation
        assertEquals(InvalidUserDocumentException.class, exception.getClass());

    }

    private User makeInputUser() {

        return User
                .builder()
                .name(USERNAME)
                .document(DOCUMENT)
                .email(USER_EMAIL)
                .build();

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

    private CheckCpfResponse makeCpfStatus(CpfStatusEnum cpfStatus) {

        return CheckCpfResponse
                .builder()
                .status(cpfStatus)
                .build();

    }

}