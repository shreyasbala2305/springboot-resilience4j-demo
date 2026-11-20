package com.backendengineering.resilience4j.service;

import com.backendengineering.resilience4j.client.DoctorServiceClient;
import com.backendengineering.resilience4j.dto.DoctorResponse;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.stereotype.Service;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class DoctorService {

    private final DoctorServiceClient doctorServiceClient;

    public DoctorService(DoctorServiceClient doctorServiceClient) {
        this.doctorServiceClient = doctorServiceClient;
    }

    @Retry(name = "doctorServiceRetry")
    @TimeLimiter(name = "doctorService")
    public CompletionStage<DoctorResponse> getDoctor(
            Long doctorId,
            long delayMs
    ) {
        return CompletableFuture.supplyAsync(
                () -> doctorServiceClient.getDoctor(doctorId, delayMs)
        );
    }
    
    @Retry(name = "doctorServiceRetry")
    public DoctorResponse getDoctorWithRetry(
            Long doctorId,
            int failuresBeforeSuccess
    ) {
        return doctorServiceClient.getDoctorWithTemporaryFailure(
                doctorId,
                failuresBeforeSuccess
        );
    }
    
    public DoctorResponse getDoctorWithInvalidRequest(Long doctorId) {
        return doctorServiceClient.getDoctorWithInvalidRequest(doctorId);
    }
    
    @CircuitBreaker(name = "doctorServiceCircuitBreaker")
    public DoctorResponse getDoctorWithCircuitBreaker(
            Long doctorId,
            long delayMs
    ) {
        return doctorServiceClient.getDoctor(doctorId, delayMs);
    }
    
    @CircuitBreaker(name = "doctorServiceCircuitBreaker")
    public DoctorResponse getDoctorWithControlledFailure(
            Long doctorId,
            boolean shouldFail
    ) {
        return doctorServiceClient.getDoctorWithFailure(
                doctorId,
                shouldFail
        );
    }
}