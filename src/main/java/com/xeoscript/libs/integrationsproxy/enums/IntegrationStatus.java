package com.xeoscript.libs.integrationsproxy.enums;

/**
 * Enum representing the various statuses of an integration request throughout its lifecycle
 */
public enum IntegrationStatus {
    /**
     * Initial status when request number is generated and request is received
     */
    INITIAL,

    /**
     * Request has been parsed successfully
     */
    PARSED,

    /**
     * Request has been validated successfully
     */
    VALIDATED,

    /**
     * API call has been initiated
     */
    API_CALL_STARTED,

    /**
     * API call completed and response received
     */
    API_CALL_COMPLETED,

    /**
     * API response has been validated successfully
     */
    API_RESPONSE_VALIDATED,

    /**
     * Final response object has been generated
     */
    RESPONSE_GENERATED,

    /**
     * Response has been serialized
     */
    RESPONSE_SERIALIZED,

    /**
     * Request processing completed successfully
     */
    COMPLETED,

    /**
     * An error occurred during processing
     */
    ERROR
}

