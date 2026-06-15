# Integration Proxy DAO Service - Complete Guide

## Overview

The Integration Proxy framework now includes comprehensive request/response status tracking through the `IntegrationProxyDAOService` interface and the `IntegrationRequestStatus` model.

## Key Components

### 1. IntegrationRequestStatus Model

A comprehensive model that tracks every aspect of a request throughout its lifecycle.

**Lombok Annotations:**
- `@Data` - Generates getters, setters, toString, equals, and hashCode
- `@Builder` - Provides builder pattern for easy object creation
- `@NoArgsConstructor` - Generates no-argument constructor
- `@AllArgsConstructor` - Generates constructor with all fields

**Date Type:** All timestamp fields use `java.util.Date`

**Fields:**

#### Basic Information
- `requestNumber`: Unique identifier for the request
- `serviceName`: Name of the integration service processing the request

#### Request/Response Data
- `rawRequestBody`: The original incoming request body (String)
- `parsedRequest`: The parsed request object (Generic type: Request)
- `apiResponse`: The API response object (Generic type: APIResponse)
- `generatedResponse`: The final response object (Generic type: Response)
- `serializedResponseBody`: The final serialized response (String)

#### Status Tracking
- `currentStatus`: Current status of the request (see IntegrationStatus enum)

#### Timestamps (java.util.Date)
- `requestReceivedAt`: When the request was first received
- `requestParsedAt`: When parsing completed
- `requestValidatedAt`: When validation completed
- `apiCallStartedAt`: When API call was initiated
- `apiCallCompletedAt`: When API call finished
- `apiResponseValidatedAt`: When API response was validated
- `responseGeneratedAt`: When final response was generated
- `responseSerializedAt`: When response was serialized
- `completedAt`: When entire processing completed

#### Error Information
- `hasError`: Boolean flag indicating if an error occurred
- `errorStep`: Which step the error occurred in
- `errorMessage`: The error message
- `errorStackTrace`: Full stack trace of the error
- `errorOccurredAt`: When the error occurred (java.util.Date)

#### Performance Metrics
- `totalProcessingTimeMs`: Total time from request received to completion (milliseconds)
- `apiCallDurationMs`: Time taken for the API call (milliseconds)

#### Additional Metadata
- `ipAddress`: Client IP address
- `userAgent`: Client user agent
- `sessionId`: Session identifier
- `userId`: User identifier

#### Audit Fields (java.util.Date)
- `createdAt`: When the record was created
- `updatedAt`: When the record was last updated
- `createdBy`: Who created the record
- `updatedBy`: Who last updated the record

### 2. IntegrationStatus Enum

Predefined status values for tracking request lifecycle:

- `INITIAL` - Request number generated and request received
- `PARSED` - Request parsed successfully
- `VALIDATED` - Request validated successfully
- `API_CALL_STARTED` - API call initiated
- `API_CALL_COMPLETED` - API call finished, response received
- `API_RESPONSE_VALIDATED` - API response validated successfully
- `RESPONSE_GENERATED` - Final response object generated
- `RESPONSE_SERIALIZED` - Response serialized to string
- `COMPLETED` - Request processing completed successfully
- `ERROR` - An error occurred during processing

### 3. IntegrationProxyDAOService Interface

The DAO service interface defines all methods for persisting and retrieving request status.

#### Methods:

##### Retrieval
```java
IntegrationRequestStatus<Request, APIResponse, Response> getRequestStatus(String requestNumber);
```
Fetch the complete status of a request by its request number.

##### Persistence (Called automatically during request processing)
- `saveInitialRequest(requestNumber, requestBody)` - Save initial request
- `saveRequestParsed(requestNumber, request)` - Update after parsing
- `saveRequestValidated(requestNumber, request)` - Update after validation
- `saveBeforeAPICall(requestNumber, request)` - Update before API call
- `saveAPIResponse(requestNumber, apiResponse)` - Save API response
- `saveAPIResponseValidated(requestNumber, apiResponse)` - Update after API validation
- `saveResponseGenerated(requestNumber, response)` - Save generated response
- `saveFinalResponse(requestNumber, serializedResponse)` - Save final response
- `saveError(requestNumber, step, error)` - Save error details

## Usage

### Step 1: Implement the DAO Service

Create a concrete implementation of `IntegrationProxyDAOService`:

```java
@Service
public class MyIntegrationDAOService 
    implements IntegrationProxyDAOService<MyRequest, MyAPIResponse, MyResponse> {
    
    @Autowired
    private IntegrationRequestRepository repository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public IntegrationRequestStatus<MyRequest, MyAPIResponse, MyResponse> getRequestStatus(String requestNumber) {
        // Fetch from database
        IntegrationRequestEntity entity = repository.findByRequestNumber(requestNumber);
        if (entity == null) {
            return null;
        }
        
        // Convert entity to status model
        return convertToStatus(entity);
    }
    
    @Override
    public void saveInitialRequest(String requestNumber, String requestBody) {
        IntegrationRequestEntity entity = new IntegrationRequestEntity();
        entity.setRequestNumber(requestNumber);
        entity.setRawRequestBody(requestBody);
        entity.setCurrentStatus(IntegrationStatus.INITIAL.name());
        entity.setRequestReceivedAt(LocalDateTime.now());
        entity.setCreatedAt(LocalDateTime.now());
        repository.save(entity);
    }
    
    @Override
    public void saveRequestParsed(String requestNumber, MyRequest request) {
        IntegrationRequestEntity entity = repository.findByRequestNumber(requestNumber);
        entity.setParsedRequest(objectMapper.writeValueAsString(request));
        entity.setCurrentStatus(IntegrationStatus.PARSED.name());
        entity.setRequestParsedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        repository.save(entity);
    }
    
    // Implement other methods similarly...
}
```

### Step 2: Use the Service

The framework automatically calls the DAO service methods during request processing. No additional code needed!

### Step 3: Query Request Status

To check the status of a request:

```java
@RestController
@RequestMapping("/api/integration-status")
public class IntegrationStatusController {
    
    @Autowired
    private MyIntegrationProxyService integrationService;
    
    @GetMapping("/{requestNumber}")
    public ResponseEntity<IntegrationRequestStatus<?, ?, ?>> getStatus(
            @PathVariable String requestNumber) {
        
        IntegrationRequestStatus<?, ?, ?> status = 
            integrationService.getRequestStatus(requestNumber);
        
        if (status == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(status);
    }
}
```

### Step 4: Database Entity Example

```java
@Entity
@Table(name = "integration_requests")
public class IntegrationRequestEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String requestNumber;
    
    private String serviceName;
    
    @Lob
    private String rawRequestBody;
    
    @Lob
    private String parsedRequest;
    
    @Lob
    private String apiResponse;
    
    @Lob
    private String generatedResponse;
    
    @Lob
    private String serializedResponseBody;
    
    private String currentStatus;
    
    // Timestamps
    private LocalDateTime requestReceivedAt;
    private LocalDateTime requestParsedAt;
    private LocalDateTime requestValidatedAt;
    private LocalDateTime apiCallStartedAt;
    private LocalDateTime apiCallCompletedAt;
    private LocalDateTime apiResponseValidatedAt;
    private LocalDateTime responseGeneratedAt;
    private LocalDateTime responseSerializedAt;
    private LocalDateTime completedAt;
    
    // Error fields
    private Boolean hasError;
    private String errorStep;
    
    @Lob
    private String errorMessage;
    
    @Lob
    private String errorStackTrace;
    
    private LocalDateTime errorOccurredAt;
    
    // Metrics
    private Long totalProcessingTimeMs;
    private Long apiCallDurationMs;
    
    // Metadata
    private String ipAddress;
    private String userAgent;
    private String sessionId;
    private String userId;
    
    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    
    // Getters and setters...
}
```

## Example Use Cases

### 1. Monitor Request Progress

```java
IntegrationRequestStatus<?, ?, ?> status = service.getRequestStatus("REQ-12345");
System.out.println("Current Status: " + status.getCurrentStatus());
System.out.println("Processing Time: " + status.getTotalProcessingTimeMs() + "ms");
```

### 2. Check for Errors

```java
IntegrationRequestStatus<?, ?, ?> status = service.getRequestStatus("REQ-12345");
if (status.getHasError()) {
    System.out.println("Error occurred at: " + status.getErrorStep());
    System.out.println("Error message: " + status.getErrorMessage());
}
```

### 3. Track Performance

```java
IntegrationRequestStatus<?, ?, ?> status = service.getRequestStatus("REQ-12345");
System.out.println("Total Time: " + status.getTotalProcessingTimeMs() + "ms");
System.out.println("API Call Time: " + status.getApiCallDurationMs() + "ms");
```

### 4. Audit Trail

```java
IntegrationRequestStatus<?, ?, ?> status = service.getRequestStatus("REQ-12345");
System.out.println("Request Received: " + status.getRequestReceivedAt());
System.out.println("Request Completed: " + status.getCompletedAt());
System.out.println("Processed By: " + status.getUserId());
```

## Benefits

✅ **Complete Visibility**: Track every step of request processing
✅ **Error Tracking**: Detailed error information with stack traces
✅ **Performance Monitoring**: Automatic timing metrics
✅ **Audit Trail**: Complete history of request lifecycle
✅ **Flexible Storage**: Implement with any database or storage solution
✅ **Optional**: Works even without DAO implementation (graceful degradation)
✅ **Type-Safe**: Generic types ensure compile-time safety
✅ **Lombok Support**: Use @Data, @Builder for clean, concise model definitions
✅ **Standard Date Types**: Uses java.util.Date for maximum compatibility

## Implementation Notes

- The DAO implementation should be in a **separate module** from this library
- This library provides only the interface (`IntegrationProxyDAOService`) and model (`IntegrationRequestStatus`)
- Use Lombok annotations (@Data, @Builder, etc.) in your entity classes for cleaner code
- All timestamp fields use `java.util.Date` for compatibility with JPA and legacy systems
- The framework automatically calls DAO methods at each processing step
- DAO service is autowired with `@Autowired(required = false)` for optional implementation

