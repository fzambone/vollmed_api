package med.voll.api.domain.doctor;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.address.CreateAddressRequestDto;

public record UpdateDoctorRequestDto(
        @NotNull
        Long id,

        String name,

        String phoneNumber,

        CreateAddressRequestDto address) {
}
