package src.domain.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import src.domain.entity.User;
import src.domain.usecase.RegistryUserUseCase;
import src.domain.repository.UserRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RegistryUserService implements RegistryUserUseCase {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository repository;

    @Override
    @Transactional
    public User registry(User user) {
        logger.info("Service - registry: User: {}", user.getName());

        return repository.registry(user);
    }

}
