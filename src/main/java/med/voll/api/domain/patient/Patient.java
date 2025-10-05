package med.voll.api.domain.patient;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.address.Address;

@Table(name = "patient")
@Entity(name = "Patient")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

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
    private String cpf;

    @Embedded
    private Address address;

    @Column(nullable = false)
    private boolean isActive;

    public Patient(@Valid CreatePatientRequestDto dto) {
        this.name = dto.name();
        this.email = dto.email();
        this.phoneNumber = dto.phoneNumber();
        this.cpf = dto.cpf();
        this.address = new Address(dto.address());
        this.isActive = true;
    }

    public void update(UpdatePatientRequestDto dto) {
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

    public void delete() { this.isActive = false; }
}
