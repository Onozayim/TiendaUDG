package com.tienda.tienda.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tienda.tienda.vars.JSONDataObject;
import com.tienda.tienda.vars.JSONMessageObject;
import com.tienda.tienda.vars.StringConsts;

@Service
public class JsonResponses {
    
    public ResponseEntity<?> ReturnErrorMessage(String message, HttpStatusCode statusCode) {
        JSONMessageObject jsonObject = new JSONMessageObject();

        jsonObject.setMessage(message);
        jsonObject.setStatus(StringConsts.Error);

        return new ResponseEntity<>(jsonObject, statusCode);
    }
    
    public ResponseEntity<?> ReturnErrorData(Object data, String message) {
        JSONDataObject jsonObject = new JSONDataObject();
        
        jsonObject.setData(data);
        jsonObject.setMessage(message);
        jsonObject.setStatus(StringConsts.Error);
        
        return new ResponseEntity<>(jsonObject, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> ReturnOkData(Object data, String message) {
        JSONDataObject jsonObject = new JSONDataObject();

        jsonObject.setData(data);
        jsonObject.setMessage(message);
        jsonObject.setStatus(StringConsts.Ok);

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    public ResponseEntity<?> ReturnOkMessage(String message) {
        JSONMessageObject jsonObject = new JSONMessageObject();

        jsonObject.setMessage(message);
        jsonObject.setStatus(StringConsts.Ok);

        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }
    
}
