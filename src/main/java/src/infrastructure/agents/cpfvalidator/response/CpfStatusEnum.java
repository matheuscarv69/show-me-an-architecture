package src.infrastructure.agents.cpfvalidator.response;

public enum CpfStatusEnum {

    ABLE_TO_VOTE,
    UNABLE_TO_VOTE;

    public static Boolean isUnable(CpfStatusEnum status) {
        return UNABLE_TO_VOTE.equals(status);
    }

}
