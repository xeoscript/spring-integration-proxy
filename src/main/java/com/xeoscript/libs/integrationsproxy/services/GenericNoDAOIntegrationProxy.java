package com.xeoscript.libs.integrationsproxy.services;

import com.xeoscript.libs.integrationsproxy.components.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

@RequiredArgsConstructor
public final class GenericNoDAOIntegrationProxy<Request, APIResponse, Response>
        extends NoDAOIntegrationProxyService<Request, APIResponse, Response> {

    private final RequestParser<Request> requestParser;

    private final RequestValidator<Request> requestValidator;

    private final APIIntegration<Request, APIResponse> apiIntegration;

    private final ResponseValidator<APIResponse> responseValidator;

    private final ResponseSerializer<Response> responseSerializer;


    @Override
    protected Request parseRequest(String requestBody, WebRequest webRequest) {
        Objects.requireNonNull(requestParser, "RequestParser cannot be null");
        Objects.requireNonNull(requestBody, "Request body cannot be null");
        Objects.requireNonNull(webRequest, "WebRequest cannot be null");
        return requestParser.parse(requestBody, webRequest);
    }

    @Override
    protected void validateRequest(Request request) {
        Objects.requireNonNull(requestValidator, "RequestValidator cannot be null");
        Objects.requireNonNull(request, "Request cannot be null");
        requestValidator.isValid(request);
    }

    @Override
    protected APIResponse performAPI(Request request) {
        Objects.requireNonNull(apiIntegration, "APIIntegration cannot be null");
        Objects.requireNonNull(request, "Request cannot be null");
        return apiIntegration.generate(request);
    }

    @Override
    protected void validateAPIResponse(APIResponse response) {
        Objects.requireNonNull(responseValidator, "ResponseValidator cannot be null");
        Objects.requireNonNull(response, "APIResponse cannot be null");
        responseValidator.isValid(response);
    }

    @Override
    protected Response generateResponse(Request request, APIResponse apiResponse) {
        Objects.requireNonNull(request, "Request cannot be null");
        Objects.requireNonNull(apiResponse, "APIResponse cannot be null");
        return null;
    }

    @Override
    protected String serializeResponse(Response response) {
        Objects.requireNonNull(responseSerializer, "ResponseSerializer cannot be null");
        Objects.requireNonNull(response, "Response cannot be null");
        return responseSerializer.serialize(response);
    }
}
