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
      return Response.ok(residents).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Get residents by flat
  @GET
  @Path("/flat/{flat}")
  public Response getResidentsByFlat(@PathParam("flat") int flat) {
    try {
      List<Residents> residents = residentsServices.getResidentsByFlat(flat);
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
      return Response.ok(residents).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Ger residents by name
  @GET
  @Path("/name/{name}")
  public Response getResidentsByName(@PathParam("name") String name) {
    try {
      List<Residents> residents = residentsServices.getResidentsByName(name);
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
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // todo: fix update resident
  // Update resident
  @PUT
  @Consumes("application/json")
  @Path("/update/{id}")
  public Response updateResident(@PathParam("id") int id, Residents updatedResident) {
    try {
      residentsServices.updateResident(updatedResident);
      return Response.status(Response.Status.OK).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }
}
