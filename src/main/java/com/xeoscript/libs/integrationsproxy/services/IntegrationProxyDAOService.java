package com.xeoscript.libs.integrationsproxy.services;

import com.xeoscript.libs.integrationsproxy.models.IntegrationRequestStatus;

public interface IntegrationProxyDAOService<Request, APIResponse, Response> {

    /**
     * Fetch the complete request status by request number
     *
     * @param requestNumber The request number to fetch
     * @return The IntegrationRequestStatus object containing all status information
     */
    IntegrationRequestStatus<Request, APIResponse, Response> getRequestStatus(String requestNumber);

    /**
     * Save the initial request with generated request number
     *
     * @param requestNumber The generated request number
     * @param requestBody   The raw request body
     */
    void saveInitialRequest(String requestNumber, String requestBody);

    /**
     * Update status after request parsing
     *
     * @param requestNumber The request number
     * @param request       The parsed request object
     */
    void saveRequestParsed(String requestNumber, Request request);

    /**
     * Update status after request validation
     *
     * @param requestNumber The request number
     * @param request       The validated request object
     */
    void saveRequestValidated(String requestNumber, Request request);

    /**
     * Update status before making API call
     *
     * @param requestNumber The request number
     * @param request       The request object being sent to API
     */
    void saveBeforeAPICall(String requestNumber, Request request);

    /**
     * Update status after receiving API response
     *
     * @param requestNumber The request number
     * @param apiResponse   The API response received
     */
    void saveAPIResponse(String requestNumber, APIResponse apiResponse);

    /**
     * Update status after API response validation
     *
     * @param requestNumber The request number
     * @param apiResponse   The validated API response
     */
    void saveAPIResponseValidated(String requestNumber, APIResponse apiResponse);

    /**
     * Update status after generating final response
     *
     * @param requestNumber The request number
     * @param response      The generated response object
     */
    void saveResponseGenerated(String requestNumber, Response response);

    /**
     * Update status with final serialized response
     *
     * @param requestNumber      The request number
     * @param serializedResponse The final serialized response string
     */
    void saveFinalResponse(String requestNumber, String serializedResponse);

    /**
     * Save error details when an exception occurs
     *
     * @param requestNumber The request number (may be null if error during generation)
     * @param step          The step where error occurred
     * @param error         The exception that occurred
     */
    void saveError(String requestNumber, String step, Exception error);
}
