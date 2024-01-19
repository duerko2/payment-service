package payment.service.events;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import payment.service.aggregate.AccountId;
import payment.service.aggregate.PaymentId;
import payment.service.aggregate.Token;

@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentSuccessful extends Event{

    private static final long serialVersionUID = 8836547826948529579L;

    private PaymentId paymentId;


}
