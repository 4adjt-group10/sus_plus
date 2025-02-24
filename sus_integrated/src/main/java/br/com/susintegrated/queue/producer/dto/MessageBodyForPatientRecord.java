package br.com.susintegrated.queue.producer.dto;

import java.util.UUID;

public record MessageBodyForPatientRecord(UUID patientRecordId, boolean isValidated, String patientName) {
}
