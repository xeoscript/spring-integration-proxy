package com.xeoscript.libs.integrationsproxy.services;

import com.xeoscript.libs.integrationsproxy.models.IntegrationRequestStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.WebRequest;

@Slf4j
public abstract class IntegrationProxyService<Request, APIResponse, Response> {

    protected IntegrationProxyDAOService<Request, APIResponse, Response> daoService;

    @Autowired(required = false)
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
     * Get the name of this integration proxy service for logging purposes
     *
     * @return The service name
     */
    protected abstract String getName();

    // ---------------------------------------------
    //   Steps Involved in Processing the Request
    // ---------------------------------------------
    // 1. Create a new Request Number
    // 2. Parse the Request
    // 3. Validate the Request
    // 4. Send the Request to the Proxy Endpoint
    // 5. Receive the Response from the Proxy Endpoint
    // 6. Parse the Response
    // 7. Validate the Response
    // 8. Create the final Response object
    // 9. Serialize the final Response object
    // 10. Return the serialized Response

    /**
     * Generate a unique request number for tracking purposes
     *
     * <p>
     * Responsibilities
     * 1. Create a unique identifier for the request
     * 2. Ensure the identifier is in the correct format
     *
     * @return The generated request number
     */
    protected abstract String generateRequestNumber();

    /**
     * Parse the incoming web request to extract the request details
     * If any encryption or signature verification is needed, it has to be done here
     *
     * <p>
     * Responsibilities
     * 1. Extract request details from the web request
     * 2. Handle any decryption
     * 3. Handle any signature verification
     *
     * @param webRequest The incoming web request
     * @return The parsed request object
     */
    protected abstract Request parseRequest(String requestBody, WebRequest webRequest);

    /**
     * Validate the parsed request to ensure it meets the required criteria
     *
     * <p>
     * Responsibilities
     * 1. Check for required fields
     * 2. Validate field formats
     *
     * @param request The parsed request object
     */
    protected void validateRequest(Request request) {
    }


    /**
     * Perform the API call to the proxy endpoint using the validated request
     *
     * <p>
     * Responsibilities
     * 1. Send the request to the proxy endpoint
     * 2. Receive the response from the proxy endpoint
     * 3. Convert the response to the APIResponse object
     *
     * @param request The validated request object
     * @return The API response object
     */
    protected abstract APIResponse performAPI(Request request);

    /**
     * Validate the API response to ensure it meets the required criteria
     *
     * <p>
     * Responsibilities
     * 1. Check for success status
     * 2. Validate response data
     *
     * @param apiResponse The API response object
     */
    protected void validateAPIResponse(APIResponse apiResponse) {
    }

    /**
     * Generate the final response object from the validated API response
     *
     * <p>
     * Responsibilities
     * 1. Map API response fields to final response fields
     * 2. Handle any additional processing needed for the final response
     *
     * @param request The original request object
     * @param apiResponse The validated API response object
     * @return The final response object
     */
    protected abstract Response generateResponse(Request request, APIResponse apiResponse);

    /**
     * Serialize the final response object to a string format for returning to the client.
     * Any Encryption and Signing needs to be handled here
     *
     * <p>
     * Responsibilities
     * 1. Convert the response object to JSON/XML/other formats as needed
     * 2. Handle any serialization errors
     * 3. Encrypt the response if needed
     * 4. Sign the response if needed
     *
     * @param response The final response object
     * @return The serialized response string
     */
    protected abstract String serializeResponse(Response response);

    // ---------------------------------------------
    //   DAO Methods for Persisting Request/Response Status
    // ---------------------------------------------

    /**
     * Save the initial request with generated request number
     *
     * @param requestNumber The generated request number
     * @param requestBody   The raw request body
     */
    protected void saveInitialRequest(String requestNumber, String requestBody) {
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
    protected void saveRequestParsed(String requestNumber, Request request) {
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
    protected void saveRequestValidated(String requestNumber, Request request) {
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
    protected void saveBeforeAPICall(String requestNumber, Request request) {
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
    protected void saveAPIResponse(String requestNumber, APIResponse apiResponse) {
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
    protected void saveAPIResponseValidated(String requestNumber, APIResponse apiResponse) {
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
    protected void saveResponseGenerated(String requestNumber, Response response) {
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
    protected void saveFinalResponse(String requestNumber, String serializedResponse) {
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
    protected void saveError(String requestNumber, String step, Exception error) {
        if (daoService != null) {
            daoService.saveError(requestNumber, step, error);
        }
    }

    public final String processRequest(String requestBody, WebRequest webRequest) {
        String name = getName();
        log.debug("[{}] - Incoming request body: {}", name, requestBody);

        String requestNumber;
        try {
            requestNumber = generateRequestNumber();
            log.debug("[{}] [{}] - Request number generated", name, requestNumber);
            saveInitialRequest(requestNumber, requestBody);
            log.debug("[{}] [{}] - Initial request saved to database", name, requestNumber);
        } catch (Exception e) {
            log.error("[{}] - Error generating request number", name, e);
            saveError(null, "generateRequestNumber", e);
            throw new RuntimeException("Failed to generate request number", e);
        }

        Request request;
        try {
            request = parseRequest(requestBody, webRequest);
            log.debug("[{}] [{}] - Request parsed successfully", name, requestNumber);
            log.debug("[{}] [{}] - Parsed request object: {}", name, requestNumber, request);
            saveRequestParsed(requestNumber, request);
            log.debug("[{}] [{}] - Parsed request saved to database", name, requestNumber);
        } catch (Exception e) {
            log.error("[{}] [{}] - Error parsing request", name, requestNumber, e);
            saveError(requestNumber, "parseRequest", e);
            throw new RuntimeException("Failed to parse request", e);
        }

        try {
            validateRequest(request);
            log.debug("[{}] [{}] - Request validated successfully", name, requestNumber);
            saveRequestValidated(requestNumber, request);
            log.debug("[{}] [{}] - Validated request saved to database", name, requestNumber);
        } catch (Exception e) {
            log.error("[{}] [{}] - Request validation failed", name, requestNumber, e);
            saveError(requestNumber, "validateRequest", e);
            throw new RuntimeException("Request validation failed", e);
        }

        APIResponse apiResponse;
        try {
            saveBeforeAPICall(requestNumber, request);
            log.debug("[{}] [{}] - Request saved before API call", name, requestNumber);
            apiResponse = performAPI(request);
            log.debug("[{}] [{}] - API call completed successfully", name, requestNumber);
            log.debug("[{}] [{}] - API response: {}", name, requestNumber, apiResponse);
            saveAPIResponse(requestNumber, apiResponse);
            log.debug("[{}] [{}] - API response saved to database", name, requestNumber);
        } catch (Exception e) {
            log.error("[{}] [{}] - Error performing API call", name, requestNumber, e);
            saveError(requestNumber, "performAPI", e);
            throw new RuntimeException("Failed to perform API call", e);
        }

        try {
            validateAPIResponse(apiResponse);
            log.debug("[{}] [{}] - API response validated successfully", name, requestNumber);
            saveAPIResponseValidated(requestNumber, apiResponse);
            log.debug("[{}] [{}] - Validated API response saved to database", name, requestNumber);
        } catch (Exception e) {
            log.error("[{}] [{}] - API response validation failed", name, requestNumber, e);
            saveError(requestNumber, "validateAPIResponse", e);
            throw new RuntimeException("API response validation failed", e);
        }

        Response response;
        try {
            response = generateResponse(request, apiResponse);
            log.debug("[{}] [{}] - Response generated successfully", name, requestNumber);
            log.debug("[{}] [{}] - Generated response object: {}", name, requestNumber, response);
            saveResponseGenerated(requestNumber, response);
            log.debug("[{}] [{}] - Generated response saved to database", name, requestNumber);
        } catch (Exception e) {
            log.error("[{}] [{}] - Error generating response", name, requestNumber, e);
            saveError(requestNumber, "generateResponse", e);
            throw new RuntimeException("Failed to generate response", e);
        }

        try {
            String serializedResponse = serializeResponse(response);
            log.debug("[{}] [{}] - Response serialized successfully", name, requestNumber);
            log.debug("[{}] [{}] - Final response body: {}", name, requestNumber, serializedResponse);
            saveFinalResponse(requestNumber, serializedResponse);
            log.debug("[{}] [{}] - Final response saved to database", name, requestNumber);
            return serializedResponse;
        } catch (Exception e) {
            log.error("[{}] [{}] - Error serializing response", name, requestNumber, e);
            saveError(requestNumber, "serializeResponse", e);
            throw new RuntimeException("Failed to serialize response", e);
        }
    }

}
