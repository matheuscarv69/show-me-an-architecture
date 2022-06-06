package src.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import src.application.controller.request.UserRequest;
import src.domain.entity.User;
import src.domain.exceptions.InvalidUserDocumentException;
import src.domain.exceptions.UserNotFoundException;
import src.domain.service.GetUserService;
import src.domain.service.RegistryUserService;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    public static final String URL = "/users";

    public static final Long ID = 1L;
    public static final String USERNAME = "James Gosling";
    public static final String DOCUMENT = "38713541048";
    public static final String USER_EMAIL = "gosling@gmail.com";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RegistryUserService registryUserService;

    @MockBean
    GetUserService getUserService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void mustToRegistryUser() throws Exception {

        // cenary
        var userRequest = makeUserRequest();
        var userRequestJson = objectMapper.writeValueAsString(userRequest);

        var registeredUser = makeUser();

        doReturn(registeredUser).when(registryUserService).registry(userRequest.toDomain());

        // action - validation
        mockMvc.perform(
                post(URL)
                        .content(userRequestJson)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

    }

    @Test
    void dontShouldToRegistryUserWhenInvalidRequest() throws Exception {

        // cenary
        var userRequest = makeInvalidUserRequest();
        var userRequestJson = objectMapper.writeValueAsString(userRequest);

        // action - validation
        mockMvc.perform(
                post(URL)
                        .content(userRequestJson)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

    }

    @Test
    void dontShouldToGetAnUserByIdWhenDocumentIsInvalid() throws Exception {

        // cenary
        var userRequest = makeUserRequest();
        var userRequestJson = objectMapper.writeValueAsString(userRequest);

        var invalidDocumentException = new InvalidUserDocumentException();

        doThrow(invalidDocumentException).when(registryUserService).registry(userRequest.toDomain());

        // action - validation
        mockMvc.perform(
                post(URL)
                        .content(userRequestJson)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

    }

    @Test
    void mustToGetAnUserById() throws Exception {

        // cenary
        var recoveredUser = makeUser();

        doReturn(recoveredUser).when(getUserService).getUserById(ID);

        // action - validation
        mockMvc.perform(
                get(URL + "/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }

    @Test
    void dontShouldToGetAnUserByIdWhenIdIsInvalid() throws Exception {

        // cenary
        var userNotFoundException = new UserNotFoundException();

        doThrow(userNotFoundException).when(getUserService).getUserById(ID);

        // action - validation
        mockMvc.perform(
                get(URL + "/" + ID)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

    }

    private UserRequest makeUserRequest() {

        return UserRequest
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

    private UserRequest makeInvalidUserRequest() {

        return UserRequest
                .builder()
                .name("")
                .document("")
                .email("")
                .build();

    }


}