package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

import org.example.core.FlatInfo;
import org.example.services.FlatInfoServices;

@Path("/flatinfo")
@Produces(MediaType.APPLICATION_JSON)
public class FlatInfoResource {

  private final FlatInfoServices flatInfoServices;

  public FlatInfoResource(FlatInfoServices flatInfoServices) {
    this.flatInfoServices = flatInfoServices;
  }

  // Create a flat info endpoint
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addFlatInfo(FlatInfo newFlatInfo) {
    try {
      flatInfoServices.addFlatInfo(newFlatInfo);
      return Response.status(Response.Status.CREATED).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Read flat info by building name and flat number endpoint
  @GET
  @Path("/{buildingName}/{flatNumber}")
  public Response getFlatInfo(
      @PathParam("buildingName") String buildingName, @PathParam("flatNumber") int flatNumber) {
    try {
      FlatInfo flatInfo = flatInfoServices.getFlatInfo(buildingName, flatNumber);
      if (flatInfo != null) {
        return Response.ok(flatInfo).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }
  // Read all flats info endpoint
  @GET
    @Path("/{buildingName}")
    public Response getAllFlatInfoForBuilding(@PathParam("buildingName") String buildingName) {
        try {
        List<FlatInfo> flatInfos = flatInfoServices.getAllFlatInfoForBuilding(buildingName);
        return Response.ok(flatInfos).build();
        } catch (SQLException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
  // Update flat info endpoint
    @PUT
    @Path("/{buildingName}/{flatNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateFlatInfo(
        @PathParam("buildingName") String buildingName,
        @PathParam("flatNumber") int flatNumber,
        FlatInfo flatInfo) {
        try {
        flatInfoServices.updateFlatInfo(buildingName, flatNumber, flatInfo);
        return Response.ok().build();
        } catch (SQLException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
  // Delete flat info endpoint
    @DELETE
    @Path("/{buildingName}/{flatNumber}")
    public Response deleteFlatInfo(
        @PathParam("buildingName") String buildingName, @PathParam("flatNumber") int flatNumber) {
        try {
        flatInfoServices.deleteFlatInfo(buildingName, flatNumber);
        return Response.ok().build();
        } catch (SQLException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
