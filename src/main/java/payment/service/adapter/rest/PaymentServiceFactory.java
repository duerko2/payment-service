package payment.service.adapter.rest;

import messaging.implementations.MessageQueueAsync;
import payment.service.repositories.PaymentReadRepo;
import payment.service.repositories.PaymentRepo;
import payment.service.service.PaymentService;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

public class PaymentServiceFactory {
	static PaymentService service = null;

	static PaymentReadRepo paymentReadRepo = null;

	static PaymentRepo paymentRepo = null;

	private final MessageQueue mq = new RabbitMqQueue("rabbitMq","event");

	public synchronized PaymentService getService(){
		if (service != null) {
			return service;
		}
		if(paymentReadRepo == null) {
			paymentReadRepo = new PaymentReadRepo(mq);
		}
		if(paymentRepo == null) {
			paymentRepo = new PaymentRepo(mq);
		}
		service = new PaymentService(mq, paymentRepo, paymentReadRepo);
		return service;
	}

	/*
	public synchronized StudentRegistrationService getService() {
		// The singleton pattern.
		// Ensure that there is at most
		// one instance of a PaymentService
		if (service != null) {
			return service;
		}
		
		// Hookup the classes to send and receive
		// messages via RabbitMq, i.e. RabbitMqSender and
		// RabbitMqListener. 
		// This should be done in the factory to avoid 
		// the PaymentService knowing about them. This
		// is called dependency injection.
		// At the end, we can use the PaymentService in tests
		// without sending actual messages to RabbitMq.
		var mq = new RabbitMqQueue("rabbitMq");
		service = new StudentRegistrationService(mq);
//		new StudentRegistrationServiceAdapter(service, mq);
		return service;
	}

	 */
}
