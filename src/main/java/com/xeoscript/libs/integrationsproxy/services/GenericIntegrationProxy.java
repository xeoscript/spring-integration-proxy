package com.xeoscript.libs.integrationsproxy.services;

import com.xeoscript.libs.integrationsproxy.components.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.WebRequest;

@RequiredArgsConstructor
public final class GenericIntegrationProxy<Request, APIResponse, Response>
        extends IntegrationProxyService<Request, APIResponse, Response> {

    private final RequestParser<Request> requestParser;

    private final RequestValidator<Request> requestValidator;

    private final APIIntegration<Request, APIResponse> apiIntegration;

    private final ResponseValidator<APIResponse> responseValidator;

    private final ResponseSerializer<Response> responseSerializer;


    @Override
    protected Request parseRequest(WebRequest webRequest) {
        return requestParser.parse(webRequest);
    }

    @Override
    protected void validateRequest(Request request) {
        requestValidator.isValid(request);
    }

    @Override
    protected APIResponse performAPI(Request request) {
        return apiIntegration.generate(request);
    }

    @Override
    protected void validateAPIResponse(APIResponse response) {
        responseValidator.isValid(response);
    }

    @Override
    protected Response generateResponse(Request request, APIResponse apiResponse) {
        return null;
    }

    @Override
    protected String serializeResponse(Response response) {
        return responseSerializer.serialize(response);
    }
}
