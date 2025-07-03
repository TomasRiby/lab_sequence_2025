package org.alticeLabs.exercise.tomas.ribeiro.exceptionHandler;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class OutOfMemoryExceptionHandler extends Throwable implements ExceptionMapper<OutOfMemoryError> {
    @Override
    public Response toResponse(OutOfMemoryError exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Out of Memory Exception: " + exception.getMessage())
                .build();
    }
}
