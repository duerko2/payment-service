package payment.service.repositories;

import messaging.MessageQueue;
import org.jmolecules.ddd.annotation.Repository;
import payment.service.aggregate.Payment;
import payment.service.aggregate.PaymentId;
import payment.service.events.PaymentCreated;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PaymentReadRepo {

    private Map<PaymentId, Payment> payments = new HashMap<>();


    public PaymentReadRepo(MessageQueue eventQueue) {
        eventQueue.addHandler(PaymentCreated.class, e -> {
            apply((PaymentCreated) e);
        });
    }
    public void apply(PaymentCreated event) {
        payments.put(event.getPaymentId(),new Payment(event.getPaymentId(), event.getAmount(), event.getMerchantId(), event.getToken()));
    }
}
