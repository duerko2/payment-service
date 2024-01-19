package payment.service.repositories;

import messaging.MessageQueue;
import payment.service.aggregate.Payment;
import payment.service.aggregate.PaymentId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PaymentRepo {
    List<Payment> payments = new ArrayList<>();
    Map<PaymentId, Payment> paymentIdToPaymentMap = new HashMap<>();

    PaymentEventStore eventStore;

    public PaymentRepo(MessageQueue bus) {
        this.eventStore = new PaymentEventStore(bus);
    }


    public void addPayment(Payment bankTransferCompletedPayment) {
        payments.add(bankTransferCompletedPayment);
    }
    //Payment payment = new Payment(); todo indsæt createNewPayment
    public void addFuturePayment(PaymentId paymentId, Payment payment) {
        paymentIdToPaymentMap.put(paymentId, payment);
    }

    public Payment getFuturePayment(PaymentId paymentId) {
        return paymentIdToPaymentMap.get(paymentId);
    }
    public List<Payment> getAllPayments() {
        List<Payment> allPayments = new ArrayList<>();
        for (Map.Entry<PaymentId, Payment> entry : paymentIdToPaymentMap.entrySet()) {
            try {
                Payment payment = entry.getValue();
                allPayments.add(payment);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
        return allPayments;
    }
    public Payment getPayment(PaymentId paymentId){
        return Payment.createFromEvents(eventStore.getEventsFor(paymentId));
    }

    public void save(Payment payment) {
        eventStore.addEvents(payment.getPaymentId(),payment.getAppliedEvents());
        payment.clearAppliedEvents();
    }
}
