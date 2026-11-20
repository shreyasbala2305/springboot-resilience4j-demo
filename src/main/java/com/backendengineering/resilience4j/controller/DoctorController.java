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
    
    @GetMapping("/{doctorId}/retry")
    public DoctorResponse getDoctorWithRetry(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "2") int failuresBeforeSuccess
    ) {
        return doctorService.getDoctorWithRetry(
                doctorId,
                failuresBeforeSuccess
        );
    }
    
    @GetMapping("/{doctorId}/circuit-breaker")
    public DoctorResponse getDoctorWithCircuitBreaker(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "1000") long delayMs
    ) {
        return doctorService.getDoctorWithCircuitBreaker(doctorId, delayMs);
    }
    
    @GetMapping("/{doctorId}/circuit-breaker/test")
    public DoctorResponse testCircuitBreaker(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "false") boolean shouldFail
    ) {
        return doctorService.getDoctorWithControlledFailure(
                doctorId,
                shouldFail
        );
    }
}