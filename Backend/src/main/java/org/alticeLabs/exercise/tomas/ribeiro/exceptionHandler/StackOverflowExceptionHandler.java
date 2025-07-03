package org.alticeLabs.exercise.tomas.ribeiro.exceptionHandler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class StackOverflowExceptionHandler implements ExceptionMapper<StackOverflowError> {
    @Override
    public Response toResponse(StackOverflowError exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Stack Overflow Exception: " + exception.getMessage())
                .build();
    }
}
