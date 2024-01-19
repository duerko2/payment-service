package payment.service.events;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import payment.service.aggregate.PaymentId;
import payment.service.aggregate.Token;

@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentRequestSent extends Event{

    private static final long serialVersionUID = -3241618877288781820L;

    private int amount;

    private Token token;

    // private String customerId;

    //  private String merchantBankId;

    // private String customerBankId;

    private PaymentId paymentId;

    private String merchantId;

}
