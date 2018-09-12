package com.benchwarmers.grads.grizzlystoreuser;

import com.benchwarmers.grads.grizzlystoreuser.entities.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonResponse {
    Map<String, Object> body = new HashMap<String, Object>();
    Map<Integer, Object> entities = new HashMap<Integer, Object>();
    Map<Integer, String> errors = new HashMap<Integer, String>();
    HttpStatus status;

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

    private void addAllErrors(List<String> listOfErrors) {
        for(Integer i = 0; i < listOfErrors.size(); i++)
            errors.put(i + 1, listOfErrors.get(i));
    }

    private void addAllEntities(List<Data> listOfEntities) {
        for(Integer i = 0; i < listOfEntities.size(); i++)
            entities.put(i + 1, listOfEntities.get(i));
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    public Map<Integer, Object> getEntities() {
        return entities;
    }

    public void setEntities(Map<Integer, Object> entities) {
        this.entities = entities;
    }

    public Map<Integer, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<Integer, String> errors) {
        this.errors = errors;
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
