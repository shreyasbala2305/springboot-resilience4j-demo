package com.backendengineering.resilience4j.client;
import java.util.concurrent.atomic.AtomicInteger;
import com.backendengineering.resilience4j.dto.DoctorResponse;
import org.springframework.stereotype.Component;

@Component
public class DoctorServiceClient {
	
	private final AtomicInteger attemptCounter = new AtomicInteger(0);
	
	public DoctorResponse getDoctorWithTemporaryFailure(
	        Long doctorId,
	        int failuresBeforeSuccess
	) {
	    int attempt = attemptCounter.incrementAndGet();

	    System.out.println("Doctor Service attempt: " + attempt);

	    if (attempt <= failuresBeforeSuccess) {
	        throw new RuntimeException("Temporary Doctor Service failure");
	    }

	    return new DoctorResponse(
	            doctorId,
	            "Dr. Sharma",
	            "Cardiology",
	            "AVAILABLE"
	    );
	}

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
    
    public DoctorResponse getDoctorWithInvalidRequest(Long doctorId) {

        System.out.println("Invalid request received.");

        throw new IllegalArgumentException(
                "Invalid doctor ID: " + doctorId
        );
    }
    
    public DoctorResponse getDoctorWithFailure(
            Long doctorId,
            boolean shouldFail
    ) {
        System.out.println("Calling Doctor Service...");

        if (shouldFail) {
            throw new RuntimeException(
                    "Simulated Doctor Service failure"
            );
        }

        return new DoctorResponse(
                doctorId,
                "Dr. Sharma",
                "Cardiology",
                "AVAILABLE"
        );
    }
}