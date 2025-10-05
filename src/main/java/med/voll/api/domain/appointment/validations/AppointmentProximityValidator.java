package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidatorException;
import med.voll.api.domain.appointment.CreateAppointmentRequestDto;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AppointmentProximityValidator implements AppointmentSchedulerValidator {

    public void validate(CreateAppointmentRequestDto dto) {
        var appointmentDate = dto.date();
        var now = LocalDateTime.now();
        var minutesDiff = Duration.between(now, appointmentDate).toMinutes();

        if (minutesDiff < 30) {
            throw new ValidatorException("Appointment needs to be at least 30 minutes from now");
        }
    }
}
