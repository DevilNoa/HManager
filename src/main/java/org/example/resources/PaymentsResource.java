package org.example.resources;

import org.example.core.Payments;
import org.example.services.PaymentsServices;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentsResource {
  private final PaymentsServices paymentsServices;

  public PaymentsResource(PaymentsServices paymentsServices) {
    this.paymentsServices = paymentsServices;
  }

  // Endpoint to create a payment
  @POST
  public Response createPayment(Payments payment) {
    try {
      paymentsServices.addPayment(payment);
      return Response.status(Response.Status.CREATED).entity(payment).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }

  // Endpoint to get a payment by ID
  @GET
  @Path("/{id}")
  public Response getPaymentById(@PathParam("id") int paymentId) {
    try {
      Payments payment = paymentsServices.getPaymentById(paymentId);
      if (payment != null) {
        return Response.ok(payment).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }

  // Endpoint to get all payments
  @GET
  public Response getAllPayments() {
    try {
      List<Payments> paymentsList = paymentsServices.getAllPayments();
      return Response.ok(paymentsList).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }

  // Endpoint to update a payment
  @PUT
  @Path("/{id}")
  public Response updatePayment(@PathParam("id") int paymentId, Payments updatedPayment) {
    try {
      Payments payment = paymentsServices.updatePayment(paymentId, updatedPayment);
      return Response.ok(payment).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }
  }

  // Endpoint to delete a payment
  @DELETE
  @Path("/{id}")
  public Response deletePayment(@PathParam("id") int paymentId) {
    try {
      boolean deleted = paymentsServices.deletePayment(paymentId);
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
