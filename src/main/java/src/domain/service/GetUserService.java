package src.domain.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import src.domain.entity.User;
import src.domain.ports.CpfValidatorIntegrationPort;
import src.domain.repository.UserRepository;
import src.domain.usecase.GetUserUseCase;

@Service
@RequiredArgsConstructor
public class GetUserService implements GetUserUseCase {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CpfValidatorIntegrationPort cpfValidator;

    private final UserRepository repository;

    @Override
    public User getUserById(Long userId) {
        logger.info("Service - get User by ID: {}", userId);

        return repository.getUserById(userId);
    }


}
