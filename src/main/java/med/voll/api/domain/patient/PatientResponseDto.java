package med.voll.api.domain.patient;

import med.voll.api.domain.address.Address;

public record PatientResponseDto(
        String name,
        String email,
        String cpf
) {
    public PatientResponseDto(Patient patient) {
        this(patient.getName(), patient.getEmail(), patient.getCpf());
    }
}
