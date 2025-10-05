package med.voll.api.domain.patient;

import aj.org.objectweb.asm.commons.Remapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findAllByIsActiveTrue(Pageable pagination);

    @Query("SELECT p.isActive FROM Patient p WHERE p.id = :id")
    boolean findIsActiveById(Long id);
}
