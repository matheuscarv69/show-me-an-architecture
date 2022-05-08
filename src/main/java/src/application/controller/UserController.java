package src.application.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import src.application.controller.request.UserRequest;
import src.application.controller.response.UserResponse;
import src.domain.usecase.GetUserUseCase;
import src.domain.usecase.RegistryUserUseCase;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RegistryUserUseCase registryUserService;

    private final GetUserUseCase getUserService;

    @PostMapping
    public ResponseEntity userRegistry(
            @RequestBody @Valid UserRequest request,
            UriComponentsBuilder uriBuilder) {

        logger.info("Controller - resgistry User: {}", request.getName());

        var user = registryUserService.registry(request.toDomain());

        var uri = uriBuilder
                .path("/users/{userId}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).build();

    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {

        logger.info("Controller - get User by ID: {}", userId);

        var user = getUserService.getUserById(userId);

        var respose = new UserResponse(user);

        return ResponseEntity.ok(respose);

    }


}
