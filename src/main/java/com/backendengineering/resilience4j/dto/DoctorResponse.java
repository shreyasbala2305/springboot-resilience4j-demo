package com.backendengineering.resilience4j.dto;

public record DoctorResponse(
        Long doctorId,
        String doctorName,
        String specialization,
        String status
) {
}