package payment.service.service;

import messaging.Event;
import messaging.MessageQueue;
import payment.service.aggregate.Payment;
import payment.service.repositories.PaymentRepo;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PaymentService {

	private MessageQueue queue;
	PaymentRepo paymentRepo = new PaymentRepo();


	public PaymentService(MessageQueue queue) {
		this.queue = queue;
		queue.addHandler("PaymentSuccessful", this::handleBankTransferCompleted);
		queue.addHandler("GetPaymentsRequest", this::handleGetPaymentsRequest);
	}

	public CompletableFuture<Payment> registerPayment(Payment payment) {
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
		System.out.println("Looking for payment belonging to event with id: " + bankTransferCompletedPayment.getPaymentId()+" and amount: "+bankTransferCompletedPayment.getAmount()+"and merchantId: "+bankTransferCompletedPayment.getMerchantId());
		CompletableFuture<Payment> p = paymentRepo.getFuturePayment(bankTransferCompletedPayment.getPaymentId());

		if(p == null) {
			System.out.println("Payment not found");
			return;
		}

		p.complete(bankTransferCompletedPayment);
	}

	//TODO: maybe not good enough, could be concurrency problems

	public void handleGetPaymentsRequest(Event event) {
		// get payments from repo
		List<Payment> paymentsList = paymentRepo.getAllPayments();

		Event responseEvent = new Event("PaymentsList", new Object[]{paymentsList});
		queue.publish(responseEvent);
	}

}