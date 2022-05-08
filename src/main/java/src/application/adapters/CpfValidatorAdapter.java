package src.application.adapters;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import src.domain.ports.CpfValidatorIntegrationPort;
import src.infrastructure.agents.cpfvalidator.CPFValidatorClient;
import src.infrastructure.agents.cpfvalidator.response.CheckCpfResponse;

@Component
@RequiredArgsConstructor
public class CpfValidatorAdapter implements CpfValidatorIntegrationPort {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CPFValidatorClient cpfValidatorClient;

    @Override
    public CheckCpfResponse checkCPF(String cpf) {
        logger.info("CPF Validator Adapter - checkCPF: {}", cpf);

        try {
            var response = cpfValidatorClient.checkCPF(cpf);

            logger.info("CPF Validator Adapter - checkCPF: {}", response.getStatus().name());

            return response;

        } catch (FeignException exception) {
            exception.getStackTrace();
        }

        return null;
    }
}
