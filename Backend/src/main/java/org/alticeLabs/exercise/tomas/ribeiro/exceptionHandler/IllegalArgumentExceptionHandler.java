package org.alticeLabs.exercise.tomas.ribeiro.exceptionHandler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionHandler implements ExceptionMapper<IllegalArgumentException> {
    @Override
    public Response toResponse(IllegalArgumentException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Illegal Argument Exception: " + exception.getMessage())
                .build();
    }
}
