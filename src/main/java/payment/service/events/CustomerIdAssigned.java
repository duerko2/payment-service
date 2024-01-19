package payment.service.events;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Value;

import payment.service.aggregate.AccountId;
import payment.service.aggregate.PaymentId;
import payment.service.aggregate.Token;

@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerIdAssigned extends Event{
    private static final long serialVersionUID = -6560261477877206113L;

    private PaymentId paymentId;


    private AccountId customerId;

    private Token token;


}
