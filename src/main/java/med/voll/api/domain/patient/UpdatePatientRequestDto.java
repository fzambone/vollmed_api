package med.voll.api.domain.patient;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.address.CreateAddressRequestDto;

public record UpdatePatientRequestDto(
        @NotNull
        Long id,

        String name,

        String phoneNumber,

        CreateAddressRequestDto address
) {
}
