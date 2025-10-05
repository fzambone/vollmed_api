package med.voll.api.domain.doctor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.address.Address;

@Table(name = "doctors")
@Entity(name = "Doctor")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String crm;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @Embedded
    private Address address;

    @Column(nullable = false)
    private boolean isActive;

    public Doctor(CreateDoctorRequestDto dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.phoneNumber = dto.phoneNumber();
        this.crm = dto.crm();
        this.specialty = dto.specialty();
        this.address = new Address(dto.address());
        this.isActive = true;
    }

    public void update(UpdateDoctorRequestDto dto) {
        if (dto.name() != null) {
            this.name = dto.name();
        }
        if (dto.phoneNumber() != null) {
            this.phoneNumber = dto.phoneNumber();
        }
        if (dto.address() != null) {
            this.address.updateData(dto.address());
        }
    }

    public void delete() {
        this.isActive = false;
    }
}
