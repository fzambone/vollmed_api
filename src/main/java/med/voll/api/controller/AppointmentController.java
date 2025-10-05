package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.appointment.AppointmentsCalendar;
import med.voll.api.domain.appointment.CreateAppointmentRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

    @Autowired
    private AppointmentsCalendar appointmentsCalendar;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid CreateAppointmentRequestDto dto) {
        var appointment = appointmentsCalendar.scheduleAppointment(dto);
        return ResponseEntity.ok(appointment);
    }
}
