package med.voll.api.domain.doctor;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAllByIsActiveTrue(Pageable pagination);

    @Query("""
            SELECT d from Doctor d
            WHERE d.isActive = true
            AND d.specialty = :specialty
            AND d.id NOT IN(
                SELECT a.doctor.id from Appointment a
                WHERE a.date = :date
            )
            ORDER BY function('RANDOM')
            LIMIT 1
            """)
    Doctor getRandomDoctorFreeOnDate(Specialty specialty, LocalDateTime date);

    @Query("SELECT d.isActive FROM Doctor d WHERE d.id = :id")
    Boolean findIsActiveById(Long id);
}
