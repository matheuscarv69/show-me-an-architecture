package src.infrastructure.agents.cpfvalidator.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CheckCpfResponse {

    private CpfStatusEnum status;

}
