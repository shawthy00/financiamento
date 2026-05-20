package com.financiamento.resource;

import com.financiamento.service.SimulacaoService;
import com.financiamento.dto.SimulacaoResponse;
import com.financiamento.dto.SimulacaoRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.validation.Valid;

@Path("/simulacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    @Inject
    SimulacaoService service;

    @POST
    public Response simular(@Valid SimulacaoRequest request) {
        SimulacaoResponse response = service.simular(request);

        return Response.status(201).entity(response).build();
    }

    @GET
    @Path("/{id}")
    public Response buscaPorId(@PathParam("id") Long id) {
        SimulacaoResponse response = service.buscaPorId(id);

        if (response == null) {
            return Response.status(404).build();
        }

        return Response.ok(response).build();
    }
}
