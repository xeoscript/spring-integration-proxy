package com.xeoscript.libs.integrationsproxy.services;

import com.xeoscript.libs.integrationsproxy.models.IntegrationRequestStatus;

public class IntegrationProxyDAODelegate<Request, APIResponse, Response> {

    private IntegrationProxyDAOService<Request, APIResponse, Response> daoService;

    public void setDaoService(IntegrationProxyDAOService<Request, APIResponse, Response> daoService) {
        this.daoService = daoService;
    }

    public IntegrationRequestStatus<Request, APIResponse, Response> getRequestStatus(String requestNumber) {
        if (daoService != null) {
            return daoService.getRequestStatus(requestNumber);
        }
        return null;
    }

    public void saveInitialRequest(String requestNumber, String requestBody) {
        if (daoService != null) {
            daoService.saveInitialRequest(requestNumber, requestBody);
        }
    }

    public void saveRequestParsed(String requestNumber, Request request) {
        if (daoService != null) {
            daoService.saveRequestParsed(requestNumber, request);
        }
    }

    public void saveRequestValidated(String requestNumber, Request request) {
        if (daoService != null) {
            daoService.saveRequestValidated(requestNumber, request);
        }
    }

    public void saveBeforeAPICall(String requestNumber, Request request) {
        if (daoService != null) {
            daoService.saveBeforeAPICall(requestNumber, request);
        }
    }

    public void saveAPIResponse(String requestNumber, APIResponse apiResponse) {
        if (daoService != null) {
            daoService.saveAPIResponse(requestNumber, apiResponse);
        }
    }

    public void saveAPIResponseValidated(String requestNumber, APIResponse apiResponse) {
        if (daoService != null) {
            daoService.saveAPIResponseValidated(requestNumber, apiResponse);
        }
    }

    public void saveResponseGenerated(String requestNumber, Response response) {
        if (daoService != null) {
            daoService.saveResponseGenerated(requestNumber, response);
        }
    }

    public void saveFinalResponse(String requestNumber, String serializedResponse) {
        if (daoService != null) {
            daoService.saveFinalResponse(requestNumber, serializedResponse);
        }
    }

    public void saveError(String requestNumber, String step, Exception error) {
        if (daoService != null) {
            daoService.saveError(requestNumber, step, error);
        }
    }
}
