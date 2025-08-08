package com.zenware.catalogo.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException ex) {
        ErrorResponse er = new ErrorResponse(404, "No encontrado");
        return Response.status(Response.Status.NOT_FOUND)
                .entity(er)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
