package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidatorException;
import med.voll.api.domain.appointment.CreateAppointmentRequestDto;
import org.springframework.stereotype.Component;
import java.time.DayOfWeek;

@Component
public class WorkingHoursValidator implements AppointmentSchedulerValidator {

    public void validate(CreateAppointmentRequestDto dto) {
        var appointmentDate = dto.date();
        var sunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeOpeningHour = appointmentDate.getHour() < 7;
        var afterClosingHour = appointmentDate.getHour() > 18;

        if (sunday || beforeOpeningHour || afterClosingHour) {
            throw new ValidatorException("Appointment out of working hours");
        }
    }
}
