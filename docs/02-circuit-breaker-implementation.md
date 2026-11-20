
# Circuit Breaker Implementation

## Objective
Implemented Resilience4j Circuit Breaker to protect the Doctor Service from repeated failures.

## Configuration
- Sliding window type: COUNT_BASED
- Sliding window size: 5
- Minimum number of calls: 5
- Failure rate threshold: 50%
- Open state wait duration: 10 seconds
- Permitted calls in half-open state: 2
- Automatic transition to half-open: Enabled

## Circuit Breaker States

### CLOSED
Requests are permitted and failures are monitored.

### OPEN
Requests are rejected to prevent calls to an unhealthy service.

### HALF_OPEN
A limited number of requests are permitted to test service recovery.

## Testing
- Introduced controlled failures in DoctorServiceClient.
- Verified Circuit Breaker state transitions using Actuator health.
- Confirmed HALF_OPEN state after the configured waiting period.

## Endpoint
GET /api/doctors/{doctorId}/circuit-breaker/test?shouldFail=true

## Learning
Circuit Breaker prevents repeated calls to an unhealthy downstream service and helps avoid cascading failures.
