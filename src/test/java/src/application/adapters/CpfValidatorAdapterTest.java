package src.application.adapters;

import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import src.infrastructure.agents.cpfvalidator.CPFValidatorClient;
import src.infrastructure.agents.cpfvalidator.response.CheckCpfResponse;
import src.infrastructure.agents.cpfvalidator.response.CpfStatusEnum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CpfValidatorAdapterTest {

    public static final String DOCUMENT = "38713541048";

    @InjectMocks
    CpfValidatorAdapter adapter;

    @Mock
    CPFValidatorClient cpfClient;

    @Test
    void mustToCheckCPF() {

        // cenary
        var expectedCpfStatus = makeCpfStatus();
        when(cpfClient.checkCPF(DOCUMENT)).thenReturn(expectedCpfStatus);

        // action
        var cpfResponse = adapter.checkCPF(DOCUMENT);

        // validation
        assertEquals(expectedCpfStatus, cpfResponse);

    }

    @Test
    void dontShouldToCheckCPFWhenThrowFeignException() {

        // cenary
        when(cpfClient.checkCPF(DOCUMENT)).thenThrow(RuntimeException.class);

        // action
        var exception = assertThrows(RuntimeException.class,
                () -> adapter.checkCPF(DOCUMENT)
        );

        // validation
        assertEquals(RuntimeException.class, exception.getClass());

    }

    private CheckCpfResponse makeCpfStatus() {

        return CheckCpfResponse
                .builder()
                .status(CpfStatusEnum.ABLE_TO_VOTE)
                .build();

    }

}