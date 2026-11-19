package com.backendengineering.resilience4j.controller;

import com.backendengineering.resilience4j.dto.DoctorResponse;
import com.backendengineering.resilience4j.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{doctorId}")
    public CompletionStage<DoctorResponse> getDoctor(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "1000") long delayMs
    ) {
        return doctorService.getDoctor(doctorId, delayMs);
    }
}