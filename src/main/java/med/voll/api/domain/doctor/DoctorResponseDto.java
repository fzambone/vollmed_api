package med.voll.api.domain.doctor;

public record DoctorResponseDto(
        Long id,
        String name,
        String email,
        String crm,
        String specialty
) {
    public DoctorResponseDto(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), String.valueOf(doctor.getSpecialty()));
    }
}
