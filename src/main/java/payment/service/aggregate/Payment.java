package payment.service.aggregate;

import com.rabbitmq.client.Address;
import org.eclipse.microprofile.openapi.models.info.Contact;
import payment.service.events.BankIdAssigned;
import payment.service.events.CustomerIdAssigned;
import payment.service.events.Event;
import payment.service.events.PaymentCreated;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue;
import lombok.AccessLevel;
import lombok.Getter;

import lombok.Setter;
import messaging.Message;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;


@AggregateRoot
@Entity
@Getter
@Setter
public class Payment {

    String merchantId;
    int amount;
    Token token;
    String customerId;
    String merchantBankId;
    String customerBankId;
    PaymentId paymentId;


    private List<Event> appliedEvents = new ArrayList<Event>();

    private Map<Class<? extends Message>, Consumer<Message>> handlers = new HashMap<>();




    //TODO consider if payment needs every variable in payment instead of the current once.
    public static Payment createPayment(int amount, Token token, AccountId merchantId, PaymentId paymentId) {
        PaymentCreated event = new PaymentCreated(amount, token, paymentId, merchantId);
        Payment p =new Payment();
        p.setPaymentId(paymentId);
        p.appliedEvents.add(event);
        return p;
    }

    public static Payment createNewPayment(Stream<Event> events) {
        Payment payment = new Payment();
        payment.applyEvents(events);

        return payment;

    }

    public Payment() {
        registerEventHandlers();
    }

    private void registerEventHandlers() {
        handlers.put(PaymentCreated.class, e -> apply((PaymentCreated) e));
        handlers.put(BankIdAssigned.class, e -> apply((BankIdAssigned) e));
        handlers.put(CustomerIdAssigned.class, e -> apply((CustomerIdAssigned) e));
    }
    public Payment(PaymentId paymentId, int amount, AccountId merchantId, Token token) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.token = token;
        this.merchantId = merchantId;
        registerEventHandlers();
    }


    private void apply(PaymentCreated event) {
        this.amount = event.getAmount();
        this.token = event.getToken();
        this.merchantId = event.getMerchantId();
        // this.customerId = event.getCustomerId();
        // this.merchantBankId = event.getMerchantBankId();
        // this.customerBankId = event.getCustomerBankId();
        this.paymentId = event.getPaymentId();
    }

    public static Payment createFromEvents(Stream<Event> events) {
        Payment payment = new Payment();
        payment.applyEvents(events);
        return payment;
    }


    private void apply(BankIdAssigned event) {
        customerBankId = event.getCustomerBankId();
        merchantBankId = event.getCustomerBankId();
    }

    private void apply(CustomerIdAssigned event) {
        customerId = event.getCustomerId();
    }


    private void missingHandler(Message e) {
        throw new RuntimeException("Missing event handler for " + e.getClass().getSimpleName());
    }

    public void clearAppliedEvents() {
        appliedEvents.clear();
    }

    public void applyEvents(Stream<Event> events) {
        events.forEach(e -> {
            this.applyEvents(e);
        });
        if (this.getPaymentId() == null) {
            throw new Error("payment does not exist");
        }
    }

    private void applyEvents(Event e) {
        handlers.getOrDefault(e.getClass(), this::missingHandler).accept(e);
    }


//resten behøves ikke
/*
    public Payment() {
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public int getAmount() {
        return amount;
    }


    public String getMerchantId() {
        return merchantId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void setAccountId(String accountId) {
        this.customerId = accountId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMerchantBankId() {
        return merchantBankId;
    }

    public void setMerchantBankId(String merchantBankId) {
        this.merchantBankId = merchantBankId;
    }

    public String getCustomerBankId() {
        return customerBankId;
    }

    public void setCustomerBankId(String customerBankId) {
        this.customerBankId = customerBankId;
    }
    public boolean equals(Object obj) {
        if (obj instanceof Payment) {
            Payment other = (Payment) obj;
            return other.amount == amount && other.merchantId.equals(merchantId);
        }
        return false;
    }*/
}
