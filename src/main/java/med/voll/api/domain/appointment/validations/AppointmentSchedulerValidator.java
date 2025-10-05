package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.appointment.CreateAppointmentRequestDto;

public interface AppointmentSchedulerValidator {

    void validate(CreateAppointmentRequestDto dto);
}
