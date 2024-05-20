package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import org.example.core.Residents;
import org.example.services.ResidentsServices;

@Path("/residents")
@Produces("application/json")
public class ResidentsResource {
  private final ResidentsServices residentsServices;

  public ResidentsResource(ResidentsServices residentsServices) {
    this.residentsServices = residentsServices;
  }

  // Get all residents
  @GET
  public Response getAllResidents() {
    try {
      List<Residents> residents = residentsServices.getAllResidents();
      if (residents.isEmpty()) {
        return Response.status(Response.Status.NOT_FOUND).entity("No residents found").build();
      }
      return Response.ok(residents).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.BAD_REQUEST).entity("Invalid request").build();
    }
  }

  // Get residents by flat
  @GET
  @Path("/flat/{flat}")
  public Response getResidentsByFlat(@PathParam("flat") int flat) {
    try {
      List<Residents> residents = residentsServices.getResidentsByFlat(flat);
      if (residents.isEmpty()) {
        return Response.status(Response.Status.NOT_FOUND)
            .entity("No residents found in this flat")
            .build();
      }
      return Response.ok(residents).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Get residents by building
  @GET
  @Path("/building/{building}")
  public Response getResidentsByBuilding(@PathParam("building") String building) {
    try {
      List<Residents> residents = residentsServices.getResidentsByBuilding(building);
      if (residents.isEmpty()) {
        return Response.status(Response.Status.NOT_FOUND)
            .entity("No residents found in this building")
            .build();
      }
      return Response.ok(residents).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Get residents by building and flat
  @GET
  @Path("/building/{building}/flat/{flat}")
  public Response getResidentsByBuildingAndFlat(
      @PathParam("building") String building, @PathParam("flat") int flat) {
    try {
      List<Residents> residents = residentsServices.getResidentsByBuildingAndFlat(building, flat);
      if (residents.isEmpty()) {
        return Response.status(Response.Status.NOT_FOUND)
            .entity("No residents found in this building and flat")
            .build();
      }
      return Response.ok(residents).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Get residents by name
  @GET
  @Path("/name/{name}")
  public Response getResidentsByName(@PathParam("name") String name) {
    try {
      List<Residents> residents = residentsServices.getResidentsByName(name);
      if (residents.isEmpty()) {
        return Response.status(Response.Status.NOT_FOUND)
            .entity("No residents found with this name")
            .build();
      }
      return Response.ok(residents).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Get residents by building and flat and name
  @GET
  @Path("/building/{building}/flat/{flat}/name/{name}")
  public Response getResidentsByBuildingAndFlatAndName(
      @PathParam("building") String building,
      @PathParam("flat") int flat,
      @PathParam("name") String name) {
    try {
      List<Residents> residents =
          residentsServices.getResidentsByBuildingAndFlatAndName(building, flat, name);
      if (residents.isEmpty()) {
        return Response.status(Response.Status.NOT_FOUND)
            .entity("No residents found in this building, flat, and with this name")
            .build();
      }
      return Response.ok(residents).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Create resident
  @POST
  @Consumes("application/json")
  public Response addResident(Residents newResident) {
    try {
      residentsServices.addResident(newResident);
      return Response.status(Response.Status.CREATED).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while processing the request").build();
    }
  }

  // Update resident
  @PUT
  @Consumes("application/json")
  @Path("/update/{id}")
  public Response updateResident(@PathParam("id") int id, Residents updatedResident) {
    try {
      residentsServices.updateResident(updatedResident);
      return Response.status(Response.Status.OK).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while processing the request").build();
    }
  }

  // Delete resident
  @DELETE
  @Path("/delete/{id}")
  public Response deleteResident(@PathParam("id") int id) {
    try {
      residentsServices.deleteResident(id);
      return Response.status(Response.Status.OK).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An error occurred while processing the request").build();
    }
  }
}
