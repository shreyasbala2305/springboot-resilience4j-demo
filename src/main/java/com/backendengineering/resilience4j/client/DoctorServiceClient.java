package com.backendengineering.resilience4j.client;

import com.backendengineering.resilience4j.dto.DoctorResponse;
import org.springframework.stereotype.Component;

@Component
public class DoctorServiceClient {

    public DoctorResponse getDoctor(Long doctorId, long delayMs) {

        System.out.println("Calling Doctor Service...");

        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(
                    "Doctor Service call was interrupted", exception
            );
        }

        System.out.println("Doctor Service responded successfully.");

        return new DoctorResponse(
                doctorId,
                "Dr. Sharma",
                "Cardiology",
                "AVAILABLE"
        );
    }
}