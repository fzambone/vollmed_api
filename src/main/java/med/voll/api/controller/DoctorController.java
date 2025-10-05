package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.doctor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/doctors")
@SecurityRequirement(name = "bearer-key")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid CreateDoctorRequestDto data, UriComponentsBuilder uriBuilder) {
        var doctor = new Doctor(data);
        doctorRepository.save(doctor);
        var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DoctorResponseDetailDto(doctor));
    }

    @GetMapping
    public ResponseEntity<Page<DoctorResponseDto>> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        var page = doctorRepository.findAllByIsActiveTrue(pagination).map(DoctorResponseDto::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDetailDto> getDetail(@PathVariable Long id) {
        var doctor = doctorRepository.getReferenceById(id);

        return ResponseEntity.ok(new DoctorResponseDetailDto(doctor));
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid UpdateDoctorRequestDto data) {
        var doctor = doctorRepository.findById(data.id())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.update(data);

        return ResponseEntity.ok(new DoctorResponseDetailDto(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        var doctor = doctorRepository.getReferenceById(id);
        doctor.delete();

        return ResponseEntity.noContent().build();
    }
}
