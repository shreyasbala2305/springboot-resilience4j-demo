package com.backendengineering.resilience4j.controller;

import com.backendengineering.resilience4j.client.DoctorServiceClient;
import com.backendengineering.resilience4j.dto.DoctorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorServiceClient doctorServiceClient;

    public DoctorController(DoctorServiceClient doctorServiceClient) {
        this.doctorServiceClient = doctorServiceClient;
    }

    @GetMapping("/{doctorId}")
    public DoctorResponse getDoctor(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "1000") long delayMs
    ) {
        return doctorServiceClient.getDoctor(doctorId, delayMs);
    }
}