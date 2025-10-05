package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidatorException;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.CreateAppointmentRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientAlreadyHasAppointmentOnDateValidator implements AppointmentSchedulerValidator {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public void validate(CreateAppointmentRequestDto dto) {
        var firstSlot = dto.date().withHour(7);
        var lastSlot = dto.date().withHour(18);
        var patientHasAnotherAppointment = appointmentRepository.existsByPatientIdAndDateBetween(dto.patientId(), firstSlot, lastSlot);
        if (patientHasAnotherAppointment) {
            throw new ValidatorException("Patient already has an appointment on the date");
        }
    }
}
