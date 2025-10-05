package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidatorException;
import med.voll.api.domain.appointment.CreateAppointmentRequestDto;
import med.voll.api.domain.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorIsActiveValidator implements AppointmentSchedulerValidator {

    @Autowired
    private DoctorRepository doctorRepository;

    public void validate(CreateAppointmentRequestDto dto) {
        if (dto.doctorId() == null) {
            return;
        }

        var doctorIsActive = doctorRepository.findIsActiveById(dto.doctorId());
        if (!doctorIsActive) {
            throw new ValidatorException("Doctor is not active");
        }
    }
}
