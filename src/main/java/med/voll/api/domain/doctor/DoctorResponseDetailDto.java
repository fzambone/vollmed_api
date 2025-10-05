package med.voll.api.domain.doctor;

import med.voll.api.domain.address.Address;

public record DoctorResponseDetailDto(Long id, String name, String email, String crm, String phoneNumber, Specialty specialty, Address address) {

    public DoctorResponseDetailDto(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getPhoneNumber(), doctor.getSpecialty(), doctor.getAddress());
    }
}
