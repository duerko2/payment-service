package payment.service.service;

import messaging.MessageQueue;
import payment.service.aggregate.AccountId;
import payment.service.aggregate.Payment;
import payment.service.aggregate.PaymentId;
import payment.service.aggregate.Token;
import payment.service.events.*;
import payment.service.repositories.PaymentReadRepo;
import payment.service.repositories.PaymentRepo;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PaymentService {

	private MessageQueue queue;

	PaymentRepo paymentRepo;
	PaymentReadRepo paymentReadRepo;



	public PaymentService(MessageQueue queue, PaymentRepo paymentRepo, PaymentReadRepo paymentReadRepo) {
		this.queue = queue;
		this.paymentRepo = paymentRepo;
		this.paymentReadRepo = paymentReadRepo;

		queue.addHandler(PaymentSuccessful.class, e -> handleBankTransferCompleted((PaymentSuccessful) e));
		queue.addHandler(GetPaymentsRequest.class, e -> handleGetPaymentsRequest((GetPaymentsRequest) e));
		//queue.addHandler(PaymentRequestSent.class, e -> handlePaymentRequest((PaymentRequestSent) e));
	}



	public Payment registerPayment(int amount, Token token, AccountId merchantId) {
		// Generate payment id
		PaymentId id = new PaymentId(UUID.randomUUID());

		// Create Payment
		Payment registeredPayment = Payment.createPayment(amount, token,merchantId, id);
		// Add payment request to repo
		//paymentRepo.addFuturePayment(id,registeredPayment);
		paymentRepo.save(registeredPayment);

		PaymentRequested paymentRequested = new PaymentRequested(registeredPayment.getAmount(), registeredPayment.getToken(), registeredPayment.getPaymentId(), registeredPayment.getMerchantId());
		queue.publish(paymentRequested);

		// Return the payment to the resource
		return registeredPayment;
	}


	public void handleBankTransferCompleted(PaymentSuccessful event) {
		PaymentSuccessful bankTransferCompletedPayment = event;
		System.out.println("Looking for payment belonging to event with id: " + bankTransferCompletedPayment.getPaymentId());
		Payment p = paymentRepo.getPayment(bankTransferCompletedPayment.getPaymentId());

		if(p == null) {
			System.out.println("Payment not found");
			return;
		}


		// TODO: Match with completablefuture ?
	}

	//TODO: maybe not good enough, could be concurrency problems

	public void handleGetPaymentsRequest(Event event) {
		// get payments from repo
	 	GetPaymentsRequest payments = new GetPaymentsRequest(paymentRepo.getAllPayments());
		queue.publish(payments);
	}

	private void handlePaymentRequest(PaymentRequestSent e) {


		System.out.println("ppooop");

	}

}