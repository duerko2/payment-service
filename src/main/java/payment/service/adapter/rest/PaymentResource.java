package payment.service.adapter.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import payment.service.aggregate.AccountId;
import payment.service.aggregate.Payment;
import payment.service.aggregate.Token;
import payment.service.service.PaymentService;

import java.util.UUID;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeoutException;

@Path("/payments")
public class PaymentResource {

	PaymentServiceFactory factory = new PaymentServiceFactory();
	PaymentService service = factory.getService();

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response registerPayment(PaymentDTO payment) {

		try {
			var p = service.registerPayment(payment.getAmount(),new Token(payment.getToken()),new AccountId(UUID.fromString(payment.getMerchantId())));
			return Response.ok(p).build();
		} catch (Exception e) {
			return Response.status(409).build();
		}
	}
}
