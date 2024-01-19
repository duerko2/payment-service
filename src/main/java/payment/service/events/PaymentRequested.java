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
public class PaymentRequested extends Event{

    private static final long serialVersionUID = -7396798038003030096L;

    private int amount;

    private Token token;

    // private String customerId;

    //  private String merchantBankId;

    // private String customerBankId;

    private PaymentId paymentId;

    AccountId merchantId;
    public boolean equals(Object obj){
        if (obj instanceof PaymentRequested) {
            PaymentRequested other = (PaymentRequested) obj;
            return other.getPaymentId().equals(this.getPaymentId());
        }
        return false;
    }

}