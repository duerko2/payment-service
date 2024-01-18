package payment.service.events;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BankIdAssigned extends Event {
    private static final long serialVersionUID = -1;

    private String merchantBankId;

    private String customerBankId;




}