package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.ValidatorException;
import med.voll.api.domain.appointment.CreateAppointmentRequestDto;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientIsActiveValidator implements AppointmentSchedulerValidator {

    @Autowired
    private PatientRepository patientRepository;

    public void validate(CreateAppointmentRequestDto dto) {
        var isPatientActive = patientRepository.findIsActiveById(dto.patientId());
        if (!isPatientActive) {
            throw new ValidatorException("Patient is not active.");
        }
    }
}
