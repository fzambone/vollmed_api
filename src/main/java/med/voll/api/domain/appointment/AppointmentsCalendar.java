package med.voll.api.domain.appointment;

import med.voll.api.domain.ValidatorException;
import med.voll.api.domain.appointment.validations.AppointmentSchedulerValidator;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentsCalendar {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private List<AppointmentSchedulerValidator> validators;

    public AppointmentDetailResponseDto scheduleAppointment(CreateAppointmentRequestDto dto) {
        if (!patientRepository.existsById(dto.patientId())) {
            throw new ValidatorException("Patient with id: " + dto.patientId() + " doest not exist");
        }

        if (dto.doctorId() != null && !doctorRepository.existsById(dto.doctorId())) {
            throw new ValidatorException("Doctor with id: " + dto.doctorId() + " doest not exist");
        }

        validators.forEach(validator -> validator.validate(dto));

        var doctor = chooseDoctor(dto);
        var patient = patientRepository.getReferenceById(dto.patientId());
        var appointment = new Appointment(null, doctor, patient, dto.date());
        appointmentRepository.save(appointment);

        return new AppointmentDetailResponseDto(appointment);
    }

    private Doctor chooseDoctor(CreateAppointmentRequestDto dto) {
        if (dto.doctorId() != null) {
            return doctorRepository.getReferenceById(dto.doctorId());
        }

        if (dto.specialty() == null) {
            throw new ValidatorException("Specialty is required when no doctor is specified");
        }

        var doctor = doctorRepository.getRandomDoctorFreeOnDate(dto.specialty(), dto.date());
        if (doctor == null) {
            throw new ValidatorException("No doctor available with the chosen specialty: " + dto.specialty());
        }

        return doctor;
    }

}
