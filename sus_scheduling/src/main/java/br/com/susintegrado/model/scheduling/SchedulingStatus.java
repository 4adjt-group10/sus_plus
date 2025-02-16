package br.com.susintegrado.model.scheduling;

public enum SchedulingStatus {

    UNDER_ANALYSIS("Em análise"),
    SCHEDULED("Agendado"),
    RESCHEDULED("Reagendado"),
    CANCELED("Cancelado"),
    DONE("Realizado"),
    ATTENDING("Atendendo"),
    LATE("Atrasado");

    private final String description;

    SchedulingStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
