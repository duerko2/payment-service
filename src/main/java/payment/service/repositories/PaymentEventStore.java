package payment.service.repositories;

import payment.service.aggregate.PaymentId;
import payment.service.events.*;

import lombok.NonNull;
import messaging.MessageQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class PaymentEventStore {

    private Map<PaymentId, List<Event>> store = new ConcurrentHashMap<>();

    private MessageQueue eventBus;

    public PaymentEventStore(MessageQueue eventBus) { this.eventBus = eventBus; }

    public void addEvent(PaymentId id, Event event) {
        if (!store.containsKey(event.getPaymentId())) {
            store.put(event.getPaymentId(), new ArrayList<Event>());
        }
        store.get(event.getPaymentId()).add(event);
        eventBus.publish(event);
    }

    public Stream<Event> getEventsFor(PaymentId id) {
        if (!store.containsKey(id)) {
            store.put(id, new ArrayList<Event>());
        }
        return store.get(id).stream();
    }

    public void addEvents(@NonNull PaymentId paymentId, List<Event> appliedEvents) {
        appliedEvents.stream().forEach(e -> addEvent(paymentId, e));
    }
}
