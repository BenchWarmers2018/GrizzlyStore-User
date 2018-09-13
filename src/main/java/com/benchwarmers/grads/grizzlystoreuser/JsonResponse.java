package com.benchwarmers.grads.grizzlystoreuser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonResponse {
    private Map<String, Object> body = new HashMap<String, Object>();
    private Map<Integer, Object> entities = new HashMap<Integer, Object>();
    private Map<Integer, String> errors = new HashMap<Integer, String>();
    private HttpStatus status;

    public JsonResponse() {
    }

    public JsonResponse(HttpStatus status, List<Data> listOfEntities, List<String> listOfErrors) {
        addAllEntities(listOfEntities);
        addAllErrors(listOfErrors);
        this.status = status;
    }

    public JsonResponse(HttpStatus status, List<Data> listOfEntitites) {
        this.status = status;
        addAllEntities(listOfEntitites);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void addErrorMessage(String error) {
        errors.put(errors.size() + 1, error);
    }

    public void addEntity(Data data) {
        entities.put(entities.size() + 1, data);
    }

    public void addAllErrors(List<String> listOfErrors) {
        for(Integer i = 0; i < listOfErrors.size(); i++)
            errors.put(i + 1, listOfErrors.get(i));
    }

    public void addAllEntities(List<Data> listOfEntities) {
        for(Integer i = 0; i < listOfEntities.size(); i++)
            entities.put(i + 1, listOfEntities.get(i));
    }

    public Map<Integer, Object> getEntities() {
        return entities;
    }

    public Map<Integer, String> getErrors() {
        return errors;
    }

    public ResponseEntity createResponse() {
        body.put("Status", status);

        if (entities.size() > 0)
            body.put("Entities", entities);

        if (errors.size() > 0)
            body.put("Errors", errors);

        return ResponseEntity.status(status).body(body);
    }
}
