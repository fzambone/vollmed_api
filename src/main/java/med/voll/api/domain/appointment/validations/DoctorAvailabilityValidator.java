package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidatorException;
import med.voll.api.domain.appointment.AppointmentRepository;
import med.voll.api.domain.appointment.CreateAppointmentRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorAvailabilityValidator implements AppointmentSchedulerValidator {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public void validate(CreateAppointmentRequestDto dto) {
        var doctorAlreadyBooked = appointmentRepository.existsByDoctorIdAndDate(dto.doctorId(), dto.date());
        if (doctorAlreadyBooked) {
            throw new ValidatorException("Doctor already booked for this time and date");
        }
    }
}
