package com.zenware.catalogo.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException ex) {
        ErrorResponse er = new ErrorResponse(400, "Datos inválidos");
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(er)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
