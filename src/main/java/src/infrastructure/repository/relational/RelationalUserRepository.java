package src.infrastructure.repository.relational;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import src.domain.entity.User;
import src.domain.exceptions.UserNotFoundException;
import src.domain.repository.UserRepository;
import src.infrastructure.model.UserModel;
import src.infrastructure.repository.jpa.UserRepositoryJPA;

@Repository
@RequiredArgsConstructor
public class RelationalUserRepository implements UserRepository {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepositoryJPA repository;

    @Override
    public User registry(User user) {
        logger.info("Repository - registry: User name: {}", user.getName());

        var userModel = new UserModel(user);

        var registeredUser = repository.save(userModel);

        return registeredUser.toDomain();
    }

    @Override
    public User getUserById(Long userId) {
        logger.info("Repository - get User by ID: {}", userId);

        var userFound = repository.findById(userId);

        if (userFound.isEmpty()) {
            throw new UserNotFoundException();
        }

        return userFound.get().toDomain();

    }
}
