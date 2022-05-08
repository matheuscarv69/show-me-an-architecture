package src.domain.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import src.domain.entity.User;
import src.domain.exceptions.InvalidUserDocumentException;
import src.domain.ports.CpfValidatorIntegrationPort;
import src.domain.repository.UserRepository;
import src.domain.usecase.RegistryUserUseCase;
import src.infrastructure.agents.cpfvalidator.response.CpfStatusEnum;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class RegistryUserService implements RegistryUserUseCase {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CpfValidatorIntegrationPort cpfValidator;

    private final UserRepository repository;

    @Override
    @Transactional
    public User registry(User user) {
        logger.info("Service - registry: User: {}", user.getName());

        verifyDocument(user);

        return repository.registry(user);
    }

    private void verifyDocument(User user) {
        var cpfStatus = cpfValidator.checkCPF(user.getDocument());

        if (CpfStatusEnum.isUnable(cpfStatus.getStatus())) {
            throw new InvalidUserDocumentException();
        }
    }

}
