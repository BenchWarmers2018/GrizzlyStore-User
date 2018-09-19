package com.benchwarmers.grads.grizzlystoreuser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonResponse {
    private Map<String, Object> body = new HashMap<String, Object>();
    private List<Object> entities = new ArrayList<>();
    private List<String> errors = new ArrayList<>();
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

    public void addErrorMessage(String error) { errors.add(error); }

    public void addEntity(Data data) {
        entities.add(data);
    }

    public void addAllErrors(List<String> listOfErrors) {
        for(String error : listOfErrors)
            errors.add(error);
    }

    public void addAllEntities(List<Data> listOfEntities) {
        for(Data entity : listOfEntities)
            entities.add(entity);
    }

    public List<Object> getEntities() {
        return entities;
    }

    public List<String> getErrors() {
        return errors;
    }

    public ResponseEntity createResponse() {
        body.put("status", status);

//        if (entities.size() > 0)
            body.put("entities", entities);

//        if (errors.size() > 0)
            body.put("errors", errors);

        return ResponseEntity.status(status).body(body);
    }
}
