package org.alticeLabs.exercise.tomas.ribeiro.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.alticeLabs.exercise.tomas.ribeiro.service.LabSequenceService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.Map;

@Path("/labseq")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Lab Sequence",
     description = "Calculate the value of the LabSeq series for a given index")
public class LabSequenceController {

    @Inject
    LabSequenceService labSequenceService;


    @GET
    @Path("{number}")
     @Operation(
        summary = "Get LabSeq value",
        description = "Returns the LabSeq value for the provided **non-negative** index"
    )
    public Map<String, String> labseq(@PathParam("number") int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number cannot be negative");
        }

        String calculatedResult = labSequenceService.calculateLabSeq(number).toString();
        System.out.println("Calculated Lab Sequence for " + number + ": " + calculatedResult);
        return Map.of("value", calculatedResult);
    }
}
