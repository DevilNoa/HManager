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
  public Response addEmploy(Employ newEmploy) {
    try {
      employServices.addEmploy(newEmploy);
      return Response.status(Response.Status.CREATED).build();
    } catch (SQLException e) {
      e.printStackTrace();
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Read an employ by name endpoint
  @GET
  @Path("/{id}")
  public Response getEmployByID(@PathParam("id") int id) {
    try {
      Employ employ = employServices.getEmployByID(id);
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
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateEmploy(@PathParam("id") Integer id, Employ newEmploy) {
    try {
      Employ employ = employServices.updateEmploy(id, newEmploy);
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
  @Path("/{id}")
  public Response deleteEmploy(@PathParam("id") int id) throws SQLException {
    boolean success = EmployServices.deleteEmploy(id);
    if (success) {
      return Response.ok("Employ deleted successfully").build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }
}
