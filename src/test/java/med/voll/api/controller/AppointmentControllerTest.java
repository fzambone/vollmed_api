package med.voll.api.controller;

import med.voll.api.domain.appointment.AppointmentDetailResponseDto;
import med.voll.api.domain.appointment.AppointmentsCalendar;
import med.voll.api.domain.appointment.CreateAppointmentRequestDto;
import med.voll.api.domain.doctor.Specialty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<CreateAppointmentRequestDto> requestJson;

    @Autowired
    private JacksonTester<AppointmentDetailResponseDto> responseJson;

    @MockBean
    private AppointmentsCalendar calendar;
    @Autowired
    private AppointmentsCalendar appointmentsCalendar;

    @Test
    @DisplayName("Should return http 400 code when data is invalid")
    @WithMockUser
    void appointments1() throws Exception {
        var response = mockMvc.perform(post("/appointments"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return http 200 code when data is valid")
    @WithMockUser
    void appointments2() throws Exception {
        var date = LocalDateTime.now().plusHours(1);
        var specialty = Specialty.CARDIOLOGY;
        var appointmentDetails = new AppointmentDetailResponseDto(null, 1l, 2l, date);

        when(appointmentsCalendar.scheduleAppointment(any())).thenReturn(appointmentDetails);

        var response = mockMvc.perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson.write(
                                new CreateAppointmentRequestDto(1l, 2l, date, specialty)
                        ).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJson = responseJson.write(appointmentDetails).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}