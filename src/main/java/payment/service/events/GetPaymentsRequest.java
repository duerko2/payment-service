package payment.service.events;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import messaging.Message;
import payment.service.aggregate.Payment;
import payment.service.aggregate.PaymentId;
import payment.service.aggregate.Token;

import java.io.Serializable;
import java.util.List;


@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GetPaymentsRequest implements Message, Serializable {

    private static final long serialVersionUID = 8576669152846687324L;
    List<Payment> paymentsList;

}
