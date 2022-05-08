package src.domain.ports;

import src.infrastructure.agents.cpfvalidator.response.CheckCpfResponse;

public interface CpfValidatorIntegrationPort {

    CheckCpfResponse checkCPF(String cpf);

}
