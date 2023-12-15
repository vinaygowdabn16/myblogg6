package com.blopapii.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

   public ResourceNotFoundException(long id){
        super("ResourceNotFound for this id: "+id);
    }
}
