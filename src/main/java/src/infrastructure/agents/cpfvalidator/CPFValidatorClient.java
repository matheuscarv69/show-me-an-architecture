package src.infrastructure.agents.cpfvalidator;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import src.infrastructure.agents.cpfvalidator.response.CheckCpfResponse;

@FeignClient(name = "CPFValidator", url = "${url.cpf.validator}")
public interface CPFValidatorClient {

    @GetMapping("/{cpf}")
    CheckCpfResponse checkCPF(@PathVariable String cpf);

}
