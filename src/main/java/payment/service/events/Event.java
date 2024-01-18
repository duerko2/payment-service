package payment.service.events;

import messaging.Message;
import lombok.Getter;
import payment.service.aggregate.PaymentId;

import java.io.Serializable;

public abstract class Event implements Message, Serializable{

    private static final long serialVersionUID = -1 ;

    private static long versionCount = 1;

    @Getter
    private final long version = versionCount++;

    //public abstract PaymentId getPaymentId();

    //public abstract MerchantId getMerchantId();

    public abstract PaymentId getPaymentId();
}
