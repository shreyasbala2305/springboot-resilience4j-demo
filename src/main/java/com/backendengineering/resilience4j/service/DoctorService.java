package com.backendengineering.resilience4j.service;

import com.backendengineering.resilience4j.client.DoctorServiceClient;
import com.backendengineering.resilience4j.dto.DoctorResponse;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Service
public class DoctorService {

    private final DoctorServiceClient doctorServiceClient;

    public DoctorService(DoctorServiceClient doctorServiceClient) {
        this.doctorServiceClient = doctorServiceClient;
    }

    @TimeLimiter(name = "doctorService")
    public CompletionStage<DoctorResponse> getDoctor(
            Long doctorId,
            long delayMs
    ) {
        return CompletableFuture.supplyAsync(
                () -> doctorServiceClient.getDoctor(doctorId, delayMs)
        );
    }
}