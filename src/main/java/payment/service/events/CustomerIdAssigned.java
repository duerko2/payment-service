package payment.service.events;
import payment.service.aggregate.CustomerId;
import javax.validation.Valid;
import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Value;

import payment.service.aggregate.PaymentId;
import payment.service.aggregate.Token;

@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerIdAssigned extends Event{

    PaymentId paymentId;


    private String customerId;

    private Token token;


}
