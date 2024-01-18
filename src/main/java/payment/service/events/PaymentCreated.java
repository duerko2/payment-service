package payment.service.events;

import payment.service.aggregate.Token;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;



@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentCreated extends Event{

    private static final long serialVersionUID = -1;

    private int amount;

    private Token token;

   // private String customerId;

  //  private String merchantBankId;

   // private String customerBankId;

    private String paymentId;

    private String merchantId;

}
