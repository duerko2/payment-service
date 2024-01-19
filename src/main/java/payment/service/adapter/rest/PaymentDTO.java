package payment.service.adapter.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PaymentDTO {
    int amount;
    String token;
    String merchantId;
}
