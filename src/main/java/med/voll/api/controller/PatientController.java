package med.voll.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.patient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/patients")
@SecurityRequirement(name = "bearer-key")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid CreatePatientRequestDto dto, UriComponentsBuilder uriBuilder) {
        var patient = new Patient(dto);
        patientRepository.save(patient);
        var uri = uriBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(uri).body(new PatientResponseDetailDto(patient));
    }

    @GetMapping
    public ResponseEntity<Page<PatientResponseDto>> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        var page = patientRepository.findAllByIsActiveTrue(pagination).map(PatientResponseDto::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid UpdatePatientRequestDto dto) {
        var patient = patientRepository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        patient.update(dto);

        return ResponseEntity.ok(new PatientResponseDetailDto(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        var patient = patientRepository.getReferenceById(id);
        patient.delete();

        return ResponseEntity.noContent().build();
    }
}
