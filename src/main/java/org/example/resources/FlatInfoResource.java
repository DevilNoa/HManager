package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.core.FlatInfo;
import org.example.services.FlatInfoServices;
import java.sql.SQLException;
import java.util.List;

@Path("/flatinfo")
@Produces(MediaType.APPLICATION_JSON)
public class FlatInfoResource {

  private final FlatInfoServices flatInfoServices;

  public FlatInfoResource(FlatInfoServices flatInfoServices) {
    this.flatInfoServices = flatInfoServices;
  }

  // Get all Flats
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllFlats() {
    try {
      List<FlatInfo> flatInfos = flatInfoServices.getAllFlats();
      if (flatInfos.isEmpty()) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("No flats found")
                .build();
      }
      return Response.ok(flatInfos).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Get all Flats by building
  @GET
  @Path("building/{buildingName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllFlatsByBuilding(@PathParam("buildingName") String buildingName) {
    try {
      List<FlatInfo> flatInfos = flatInfoServices.getAllFlatsByBuilding(buildingName);
      if (flatInfos.isEmpty()) {
        return Response.status(Response.Status.NOT_FOUND)
            .entity("No flats found in this building")
            .build();
      }
      return Response.ok(flatInfos).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Get all Flats by building and apartment number
  @GET
  @Path("building/{buildingName}/apartmentNumber/{apartmentNumber}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllFlatsByBuildingAndFlatNum(
      @PathParam("buildingName") String buildingName,
      @PathParam("apartmentNumber") int apartmentNumber) {
    try {
      List<FlatInfo> flatInfos =
          flatInfoServices.getAllFlatsByBuildingAndFlatNum(buildingName, apartmentNumber);
      if (flatInfos.isEmpty()) {
        return Response.status(Response.Status.NOT_FOUND)
            .entity("No flats found in this building with this apartment number")
            .build();
      }
      return Response.ok(flatInfos).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Create a new Flat
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addFlat(FlatInfo newFlat) {
    try {
      flatInfoServices.addFlat(newFlat);
      return Response.status(Response.Status.CREATED).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Update a Flat
  @PUT
  @Path("building/{buildingName}/apartmentNumber/{apartmentNumber}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateFlat(
      @PathParam("buildingName") String buildingName,
      @PathParam("apartmentNumber") Integer flatNumber,
      FlatInfo newFlat) {
    try {
      // Use the new apartment number from the newFlat object
      int newApartmentNumber = newFlat.getFlatNumber();

      FlatInfo flatInfo = flatInfoServices.updateFlat(buildingName, newApartmentNumber, newFlat);
      if (flatInfo != null) {
        return Response.ok(flatInfo).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PUT
  @Path("update/building/{buildingName}/apartmentNumber/{apartmentNumber}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateFlatWithNumber(
      @PathParam("buildingName") String buildingName,
      @PathParam("apartmentNumber") Integer flatNumber,
      FlatInfo newFlat) {
    try {
      // Use the current apartment number from the path variable
      FlatInfo flatInfo = flatInfoServices.updateFlat(buildingName, flatNumber, newFlat);
      if (flatInfo != null) {
        return Response.ok(flatInfo).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Delete a Flat
  @DELETE
  @Path("/{buildingName}/{apartmentNumber}")
  public Response deleteFlat(
      @PathParam("buildingName") String buildingName,
      @PathParam("apartmentNumber") int apartmentNumber) {
    try {
      boolean deleted = flatInfoServices.deleteFlat(buildingName, apartmentNumber);
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
