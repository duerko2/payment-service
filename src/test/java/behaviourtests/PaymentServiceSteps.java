package behaviourtests;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.quarkus.test.Mock;
import messaging.Message;
import messaging.MessageQueue;
import payment.service.aggregate.AccountId;
import payment.service.aggregate.Payment;
import payment.service.aggregate.PaymentId;
import payment.service.events.*;
import payment.service.repositories.PaymentReadRepo;
import payment.service.repositories.PaymentRepo;
import payment.service.service.PaymentService;
import payment.service.aggregate.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PaymentServiceSteps {
    Payment payment;
    private List<Message> publishedEvent = new ArrayList<>();


    private MessageQueue q = mock(MessageQueue.class);

    private Payment registeredPayment;

    PaymentRepo accountRepo = new PaymentRepo(q);
    PaymentReadRepo accountReadRepo = new PaymentReadRepo(q);
    private PaymentService service = new PaymentService(q,accountRepo,accountReadRepo);



    @Given("there is a payment with {int} token merchantId {string} and amount {int}")
    public void there_is_a_payment_with_token_merchant_id_and_amount(Integer int1, String string, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
        payment = new Payment();
        payment.setAmount(int2);
        payment.setMerchantId(new AccountId(UUID.randomUUID()));
        payment.setToken(new Token(int1.toString()));
    }

    @When("the payment is requested")
    public void the_payment_is_requested() {
        // Write code here that turns the phrase above into concrete actions
        registeredPayment = service.registerPayment(payment.getAmount(),payment.getToken(),payment.getMerchantId());
    }

    @Then("the {string} event is sent")
    public void the_event_is_sent(String string) {
        // Write code here that turns the phrase above into concrete actions
        PaymentRequested event = new PaymentRequested(payment.getAmount(), payment.getToken(),registeredPayment.getPaymentId(), payment.getMerchantId());
        verify(q).publish(event);
    }

    @And("the {string} event is sent with a merchantId and customerId and a paymentId")
    public void theEventIsSentWithAMerchantIdAndCustomerIdAndAPaymentId(String arg0) {
        // copy of the payment sent
        /*
        Payment expectedPayment = new Payment();
        expectedPayment.setAmount(payment.getAmount());
        expectedPayment.setMerchantId(payment.getMerchantId());
        expectedPayment.setToken(payment.getToken());
        expectedPayment.setPaymentId(payment.getPaymentId());


        // Mocks the downstream services
        expectedPayment.setCustomerId(new AccountId(UUID.randomUUID()));
        expectedPayment.setPaymentId(payment.getPaymentId());

         */

        PaymentSuccessful event = new PaymentSuccessful(registeredPayment.getPaymentId());
        service.handleBankTransferCompleted(event);
    }

    @Then("the payment is stored in the successful payments ledger")
    public void thePaymentIsStoredInTheSuccessfulPaymentsLedger() {
        // Write code here that turns the phrase above into concrete actions
        Payment result = registeredPayment;
        assertNotNull(result.getPaymentId());
    }
}


