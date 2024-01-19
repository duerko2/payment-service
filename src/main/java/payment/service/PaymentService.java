package payment.service;

import messaging.Event;
import messaging.MessageQueue;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
/**
 * @Author: Alex Batten
 * Mob programming, all members
 */
public class PaymentService {

	private MessageQueue queue;
	PaymentRepo paymentRepo = new PaymentRepo();


	public PaymentService(MessageQueue queue) {
		this.queue = queue;
		queue.addHandler("PaymentSuccessful", this::handleBankTransferCompleted);
		queue.addHandler("GetPaymentsRequest", this::handleGetPaymentsRequest);
	}

	public CompletableFuture<Payment> registerPayment(Payment payment) {
		System.out.printf("Token before send"+payment.getToken().toString());

		CompletableFuture<Payment> completedPayment = new CompletableFuture<>();

		// Generate payment id
		String id = UUID.randomUUID().toString();
		payment.setPaymentId(id);

		// Add payment request to repo
		paymentRepo.addFuturePayment(id, completedPayment);

		// Event to token service
		Event tokenEvent = new Event("PaymentRequestSent", new Object[] {payment});

		queue.publish(tokenEvent);

		// Return the completable future to the resource
		return completedPayment.orTimeout(10, java.util.concurrent.TimeUnit.SECONDS);
	}


	public void handleBankTransferCompleted(Event event) {
		var bankTransferCompletedPayment = event.getArgument(0, Payment.class);
		CompletableFuture<Payment> p = paymentRepo.getFuturePayment(bankTransferCompletedPayment.getPaymentId());

		if(p == null) {
			return;
		}

		p.complete(bankTransferCompletedPayment);
	}

	public void handleGetPaymentsRequest(Event event) {
		// get payments from repo
		List<Payment> paymentsList = paymentRepo.getAllPayments();
		Event responseEvent = new Event("PaymentsList", new Object[]{paymentsList});
		queue.publish(responseEvent);
	}

}
