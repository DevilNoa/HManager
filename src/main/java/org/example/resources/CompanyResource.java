package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import org.example.core.Company;
import org.example.services.CompanyServices;

@Path("/company")
@Produces(MediaType.APPLICATION_JSON)
public class CompanyResource {

  private final CompanyServices companyServices;

  public CompanyResource(CompanyServices companyServices) {
    this.companyServices = companyServices;
  }

  // Create a company endpoint
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response addCompany(Company newCompany) {
    try {
      companyServices.addCompany(newCompany);
      return Response.status(Response.Status.CREATED).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Read a company by name endpoint
  @GET
  @Path("/{name}")
  public Response getCompanyByName(@PathParam("name") String name) {
    try {
      Company company = companyServices.getCompanyByName(name);
      if (company != null) {
        return Response.ok(company).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Read all companies endpoint
  @GET
  public Response getAllCompanies() {
    try {
      List<Company> companies = companyServices.getAllCompanies();
      return Response.ok(companies).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Update a company endpoint
  @PUT
  @Path("/{name}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateCompany(@PathParam("name") String name, Company updatedCompany) {
    try {
      Company existingCompany = companyServices.updateCompany(name, updatedCompany);
      if (existingCompany != null) {
        return Response.ok(existingCompany).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // Delete a company endpoint
  @DELETE
  @Path("/{name}")
  public Response deleteCompany(@PathParam("name") String name) {
    try {
      boolean deleted = companyServices.deleteCompany(name);
      if (deleted) {
        return Response.status(Response.Status.OK).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }
}
