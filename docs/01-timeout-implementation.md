# 01 — Timeout Implementation

## Objective

Implement request timeout protection using Resilience4j TimeLimiter in a Spring Boot application.

## Scenario

Appointment Service communicates with Doctor Service to retrieve doctor information.

Doctor Service may respond slowly. Waiting indefinitely can consume application resources and contribute to cascading failures.

## Technology

- Java 25
- Spring Boot 4.1.2
- Resilience4j 2.4.0
- CompletableFuture
- Spring Web MVC

## Implementation

### TimeLimiter Configuration

The Doctor Service TimeLimiter is configured with a maximum duration of 2 seconds.

```yaml
resilience4j:
  timelimiter:
    instances:
      doctorService:
        timeout-duration: 2s
        cancel-running-future: true
```

### Service Implementation

The DoctorService method returns a CompletionStage and uses CompletableFuture.supplyAsync() to execute the simulated downstream call asynchronously.

The `@TimeLimiter` annotation applies the configured timeout.

### Failure Simulation

The simulated Doctor Service uses Thread.sleep(delayMs) to introduce artificial latency.

## Test Scenarios

| Scenario | Delay | Expected behavior |
|---|---:|---|
| Fast response | 1 second | Successful response |
| Slow response | 5 seconds | Timeout after approximately 2 seconds |

## Important Concepts

- TimeLimiter controls the maximum waiting duration for asynchronous operations.
- CompletableFuture provides asynchronous execution.
- Timeout does not guarantee that the underlying operation has stopped.
- Cancellation is best-effort and depends on the underlying task and execution mechanism.

## Learning Outcome

Implemented asynchronous timeout protection using Resilience4j TimeLimiter and understood how timeout behavior helps protect a service from slow downstream dependencies.

## Future Improvements

- Global exception handling
- Consistent timeout API response
- Unit testing
- Integration with Retry and Circuit Breaker