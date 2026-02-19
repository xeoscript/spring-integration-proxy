package com.xeoscript.libs.integrationsproxy.services;

import com.xeoscript.libs.integrationsproxy.models.IntegrationRequestStatus;

public class IntegrationProxyDAODelegate<Request, APIResponse, Response> {

    private IntegrationProxyDAOService<Request, APIResponse, Response> daoService;

    public void setDaoService(IntegrationProxyDAOService<Request, APIResponse, Response> daoService) {
        this.daoService = daoService;
    }

    /**
     * Get the current status of a request by request number
     *
     * @param requestNumber The request number to fetch
     * @return The IntegrationRequestStatus object containing all status information, or null if DAO service is not available
     */
    public IntegrationRequestStatus<Request, APIResponse, Response> getRequestStatus(String requestNumber) {
        if (daoService != null) {
            return daoService.getRequestStatus(requestNumber);
        }
        return null;
    }

    /**
     * Save the initial request with generated request number
     *
     * @param requestNumber The generated request number
     * @param requestBody   The raw request body
     */
    public void saveInitialRequest(String requestNumber, String requestBody) {
        if (daoService != null) {
            daoService.saveInitialRequest(requestNumber, requestBody);
        }
    }

    /**
     * Update status after request parsing
     *
     * @param requestNumber The request number
     * @param request       The parsed request object
     */
    public void saveRequestParsed(String requestNumber, Request request) {
        if (daoService != null) {
            daoService.saveRequestParsed(requestNumber, request);
        }
    }

    /**
     * Update status after request validation
     *
     * @param requestNumber The request number
     * @param request       The validated request object
     */
    public void saveRequestValidated(String requestNumber, Request request) {
        if (daoService != null) {
            daoService.saveRequestValidated(requestNumber, request);
        }
    }

    /**
     * Update status before making API call
     *
     * @param requestNumber The request number
     * @param request       The request object being sent to API
     */
    public void saveBeforeAPICall(String requestNumber, Request request) {
        if (daoService != null) {
            daoService.saveBeforeAPICall(requestNumber, request);
        }
    }

    /**
     * Update status after receiving API response
     *
     * @param requestNumber The request number
     * @param apiResponse   The API response received
     */
    public void saveAPIResponse(String requestNumber, APIResponse apiResponse) {
        if (daoService != null) {
            daoService.saveAPIResponse(requestNumber, apiResponse);
        }
    }

    /**
     * Update status after API response validation
     *
     * @param requestNumber The request number
     * @param apiResponse   The validated API response
     */
    public void saveAPIResponseValidated(String requestNumber, APIResponse apiResponse) {
        if (daoService != null) {
            daoService.saveAPIResponseValidated(requestNumber, apiResponse);
        }
    }

    /**
     * Update status after generating final response
     *
     * @param requestNumber The request number
     * @param response      The generated response object
     */
    public void saveResponseGenerated(String requestNumber, Response response) {
        if (daoService != null) {
            daoService.saveResponseGenerated(requestNumber, response);
        }
    }

    /**
     * Update status with final serialized response
     *
     * @param requestNumber      The request number
     * @param serializedResponse The final serialized response string
     */
    public void saveFinalResponse(String requestNumber, String serializedResponse) {
        if (daoService != null) {
            daoService.saveFinalResponse(requestNumber, serializedResponse);
        }
    }

    /**
     * Save error details when an exception occurs
     *
     * @param requestNumber The request number (may be null if error during generation)
     * @param step          The step where error occurred
     * @param error         The exception that occurred
     */
    public void saveError(String requestNumber, String step, Exception error) {
        if (daoService != null) {
            daoService.saveError(requestNumber, step, error);
        }
    }
}
