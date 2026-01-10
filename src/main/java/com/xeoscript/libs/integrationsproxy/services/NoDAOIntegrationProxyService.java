package com.xeoscript.libs.integrationsproxy.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.WebRequest;

@Slf4j
public abstract class NoDAOIntegrationProxyService<Request, APIResponse, Response> {

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

    public final String processRequest(String requestBody, WebRequest webRequest) {
        String requestNumber = generateRequestNumber();

        Request request = parseRequest(requestBody, webRequest);
        validateRequest(request);

        APIResponse apiResponse = performAPI(request);
        validateAPIResponse(apiResponse);

        Response response = generateResponse(request, apiResponse);

        return serializeResponse(response);
    }

}
