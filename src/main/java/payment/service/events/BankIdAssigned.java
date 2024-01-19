package payment.service.events;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import payment.service.aggregate.PaymentId;

@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BankIdAssigned extends Event {
    private static final long serialVersionUID = -7167454084952715059L;

    private String merchantBankId;

    private String customerBankId;

    private PaymentId paymentId;


}