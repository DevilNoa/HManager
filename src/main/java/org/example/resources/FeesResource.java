package org.example.resources;

import org.example.core.Fees;
import org.example.services.FeesServices;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/fees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeesResource {
  private final FeesServices feesServices;

  public FeesResource(FeesServices feesServices) {
    this.feesServices = feesServices;
  }

  // Endpoint to create a fee
  @POST
  public Response createFee(Fees fee) {
    try {
      feesServices.addFee(fee);
      return Response.status(Response.Status.CREATED).entity(fee).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }

  // Endpoint to get a fee by ID
  @GET
  @Path("/{id}")
  public Response getFeeById(@PathParam("id") int feeId) {
    try {
      Fees fee = feesServices.getFeeById(feeId);
      if (fee != null) {
        return Response.ok(fee).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }

  // Endpoint to get all fees
  @GET
  public Response getAllFees() {
    try {
      List<Fees> feesList = feesServices.getAllFees();
      return Response.ok(feesList).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }

  // Endpoint to update a fee
  @PUT
  @Path("/{id}")
  public Response updateFee(@PathParam("id") int feeId, Fees updatedFee) {
    try {
      Fees fee = feesServices.updateFee(feeId, updatedFee);
      return Response.ok(fee).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }

  // Endpoint to delete a fee
  @DELETE
  @Path("/{id}")
  public Response deleteFee(@PathParam("id") int feeId) {
    try {
      boolean deleted = feesServices.deleteFee(feeId);
      if (deleted) {
        return Response.status(Response.Status.NO_CONTENT).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }
}
