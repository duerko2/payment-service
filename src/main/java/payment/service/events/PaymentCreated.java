package payment.service.events;

import payment.service.aggregate.AccountId;
import payment.service.aggregate.PaymentId;
import payment.service.aggregate.Token;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;



@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PaymentCreated extends Event{

    private static final long serialVersionUID = 6666431182531156448L;

    private int amount;

    private Token token;

   // private String customerId;

  //  private String merchantBankId;

   // private String customerBankId;

    private PaymentId paymentId;

    AccountId merchantId;

}
