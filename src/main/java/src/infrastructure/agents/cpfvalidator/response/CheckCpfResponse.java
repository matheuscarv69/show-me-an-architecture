package src.infrastructure.agents.cpfvalidator.response;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class CheckCpfResponse {

    private CpfStatusEnum status;

}
