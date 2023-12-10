package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import org.example.core.Building;
import org.example.services.BuildingServices;

@Path("/building")
@Produces(MediaType.APPLICATION_JSON)
public class BuildingResource {
  private final BuildingServices buildingServices;

  public BuildingResource(BuildingServices buildingServices) {
    this.buildingServices = buildingServices;
  }

  // Create a building endpoint

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addBuilding(Building newBuilding) {
    try {
      buildingServices.addBuilding(newBuilding);
      return Response.status(Response.Status.CREATED).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Read a building by name endpoint
  // conflict when building name = test
  @GET
  @Path("/name/{name}")
  public Response getBuildingByName(@PathParam("name") String name) {
    try {
      Building building = buildingServices.getBuildingByName(name);
      if (building != null) {
        return Response.ok(building).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Read all buildings endpoint
  @GET
  public Response getAllBuildings() {
    try {
      List<Building> buildings = buildingServices.getAllBuildings();
      return Response.ok(buildings).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Update a building endpoint
  // todo:it not updating the database
  @PUT
  @Path("/{name}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateBuilding(@PathParam("name") String name, Building newBuilding) {
    try {
      Building updatedBuilding = buildingServices.updateBuilding(name, newBuilding);

      if (updatedBuilding != null) {
        return Response.ok(updatedBuilding).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Delete a building endpoint
  @DELETE
  @Path("/{name}")
  public Response deleteBuilding(@PathParam("name") String name) {
    try {
      boolean deleted = buildingServices.deleteBuilding(name);
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
