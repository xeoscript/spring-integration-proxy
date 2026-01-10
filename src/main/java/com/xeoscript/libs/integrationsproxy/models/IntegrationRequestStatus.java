package com.xeoscript.libs.integrationsproxy.models;

import com.xeoscript.libs.integrationsproxy.enums.IntegrationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegrationRequestStatus<Request, APIResponse, Response> {

    // Basic Request Information
    private String requestNumber;
    private String serviceName;

    // Request Body
    private String rawRequestBody;

    // Request Object (Parsed)
    private Request parsedRequest;

    // API Response Object
    private APIResponse apiResponse;

    // Final Response Object
    private Response generatedResponse;

    // Final Response Body (Serialized)
    private String serializedResponseBody;

    // Status Tracking
    private IntegrationStatus currentStatus; // INITIAL, PARSED, VALIDATED, API_CALLED, API_VALIDATED, RESPONSE_GENERATED, COMPLETED, ERROR

    // Timestamps for each step
    private Date requestReceivedAt;
    private Date requestParsedAt;
    private Date requestValidatedAt;
    private Date apiCallStartedAt;
    private Date apiCallCompletedAt;
    private Date apiResponseValidatedAt;
    private Date responseGeneratedAt;
    private Date responseSerializedAt;
    private Date completedAt;

    // Error Information
    private Boolean hasError;
    private String errorStep;
    private String errorMessage;
    private String errorStackTrace;
    private Date errorOccurredAt;

    // Processing Metrics
    private Long totalProcessingTimeMs;
    private Long apiCallDurationMs;

    // Additional Metadata
    private String ipAddress;
    private String userAgent;
    private String sessionId;
    private String userId;

    // Audit Fields
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;
}
