package src.application.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import src.application.controller.request.UserRequest;
import src.domain.usecase.RegistryUserUseCase;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RegistryUserUseCase registryUserService;

    @PostMapping
    public ResponseEntity userRegistry(@RequestBody UserRequest request, UriComponentsBuilder uriBuilder) {
        logger.info("Controller - resgistry User: {}", request.getName());

        var user = registryUserService.registry(request.toDomain());

        var uri = uriBuilder
                .path("/users/{userId}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).build();

    }


}
