package med.voll.api.domain.doctor;

import med.voll.api.domain.address.CreateAddressRequestDto;
import med.voll.api.domain.appointment.Appointment;
import med.voll.api.domain.patient.CreatePatientRequestDto;
import med.voll.api.domain.patient.Patient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Should return null when then only existing doctor is not available")
    void getRandomDoctorFreeOnDate1() {
        var nextMonday10AM = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var doctor = createDoctor("Test doctor", "doctor@voll.med", "123456", Specialty.CARDIOLOGY);
        var patient = createPatient("Test patient", "patient@voll.med", "12345678911");
        createAppointment(doctor, patient, nextMonday10AM);

        var freeDoctor = doctorRepository.getRandomDoctorFreeOnDate(Specialty.CARDIOLOGY, nextMonday10AM);
        assertThat(freeDoctor).isNull();
    }

    @Test
    @DisplayName("Should return doctor when available on date")
    void getRandomDoctorFreeOnDate2() {
        var nextMonday10AM = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var doctor = createDoctor("Test doctor", "doctor@voll.med", "123456", Specialty.CARDIOLOGY);

        var freeDoctor = doctorRepository.getRandomDoctorFreeOnDate(Specialty.CARDIOLOGY, nextMonday10AM);
        assertThat(freeDoctor).isEqualTo(doctor);
    }

    private Doctor createDoctor(String name, String email, String crm, Specialty specialty) {
        var doctor = new Doctor(doctorDto(name, email, crm, specialty));
        entityManager.persist(doctor);
        return doctor;
    }

    private Patient createPatient(String name, String email, String cpf) {
        var patient = new Patient(patientDto(name, email, cpf));
        entityManager.persist(patient);
        return patient;
    }

    private void createAppointment(Doctor doctor, Patient patient, LocalDateTime date) {
        entityManager.persist(new Appointment(null, doctor, patient, date));
    }

    private CreatePatientRequestDto patientDto(String name, String email, String cpf) {
        return new CreatePatientRequestDto(
                name,
                email,
                "99999999999",
                cpf,
                addressDto(),
                true
        );
    }

    private CreateDoctorRequestDto doctorDto(String name, String email, String crm, Specialty specialty) {
        return new CreateDoctorRequestDto(
                name,
                email,
                "99999999999",
                crm,
                specialty,
                addressDto(),
                true
        );
    }

    private CreateAddressRequestDto addressDto() {
        return new CreateAddressRequestDto(
                "Rua test",
                "Bairro test",
                "99999999",
                "São Paulo",
                "SP",
                null,
                null
        );
    }
}