package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import org.example.core.Employ;
import org.example.services.EmployServices;

@Path("/employ")
@Produces(MediaType.APPLICATION_JSON)
public class EmployResource {
  private final EmployServices employServices;

  public EmployResource(EmployServices employServices) {
    this.employServices = employServices;
  }

  // Create a employ endpoint
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addEmploy(Employ newEmploy) {
    try {
      employServices.addEmploy(newEmploy);
      return Response.status(Response.Status.CREATED).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Read an employ by name endpoint
  @GET
  @Path("/{name}")
  public Response getEmployByName(@PathParam("name") String name) {
    try {
      Employ employ = employServices.getEmployByName(name);
      if (employ != null) {
        return Response.ok(employ).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Read all employ endpoint
  @GET
  public Response getAllEmploys() {
    try {
      List<Employ> employs = employServices.getAllEmploys();
      return Response.ok(employs).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Update a employ endpoint
  @PUT
  @Path("/{name}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateEmploy(@PathParam("name") String name, Employ newEmploy) {
    try {
      Employ employ = employServices.updateEmploy(name, newEmploy);
      if (employ != null) {
        return Response.ok(employ).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Delete a employ endpoint
  @DELETE
  @Path("/{name}")
  public Response deleteEmploy(@PathParam("name") String name) {
    try {
      boolean deleted = employServices.deleteEmploy(name);
      if (deleted) {
        return Response.ok().build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }
}
