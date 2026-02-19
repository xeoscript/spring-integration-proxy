package com.xeoscript.libs.integrationsproxy.services;

import com.xeoscript.libs.integrationsproxy.components.*;

import java.util.Objects;

public class IntegrationProxyBuilder<Request, APIResponse, Response> {

    private RequestNumberGenerator requestNumberGenerator;
    private RequestParser<Request> requestParser;
    private RequestValidator<Request> requestValidator;
    private APIIntegration<Request, APIResponse> apiIntegration;
    private ResponseValidator<APIResponse> responseValidator;
    private ResponseSerializer<Response> responseSerializer;
    private String name;

    public IntegrationProxyBuilder<Request, APIResponse, Response> setRequestNumberGenerator(RequestNumberGenerator requestNumberGenerator) {
        this.requestNumberGenerator = requestNumberGenerator;
        return this;
    }

    public IntegrationProxyBuilder<Request, APIResponse, Response> setRequestParser(RequestParser<Request> requestParser) {
        this.requestParser = requestParser;
        return this;
    }

    public IntegrationProxyBuilder<Request, APIResponse, Response> setRequestValidator(RequestValidator<Request> requestValidator) {
        this.requestValidator = requestValidator;
        return this;
    }

    public IntegrationProxyBuilder<Request, APIResponse, Response> setAPIIntegration(APIIntegration<Request, APIResponse> apiIntegration) {
        this.apiIntegration = apiIntegration;
        return this;
    }

    public IntegrationProxyBuilder<Request, APIResponse, Response> setResponseValidator(ResponseValidator<APIResponse> responseValidator) {
        this.responseValidator = responseValidator;
        return this;
    }

    public IntegrationProxyBuilder<Request, APIResponse, Response> setResponseSerializer(ResponseSerializer<Response> responseSerializer) {
        this.responseSerializer = responseSerializer;
        return this;
    }

    public IntegrationProxyBuilder<Request, APIResponse, Response> setName(String name) {
        this.name = name;
        return this;
    }

    private void validate() {
        Objects.requireNonNull(requestNumberGenerator, "RequestNumberGenerator cannot be null");
        Objects.requireNonNull(requestParser, "RequestParser cannot be null");
        Objects.requireNonNull(requestValidator, "RequestValidator cannot be null");
        Objects.requireNonNull(apiIntegration, "APIIntegration cannot be null");
        Objects.requireNonNull(responseValidator, "ResponseValidator cannot be null");
        Objects.requireNonNull(responseSerializer, "ResponseSerializer cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");
    }

    public GenericIntegrationProxy<Request, APIResponse, Response> build() {
        validate();
        return new GenericIntegrationProxy<>(
            requestNumberGenerator,
            requestParser,
            requestValidator,
            apiIntegration,
            responseValidator,
            responseSerializer,
            name
        );
    }
}
