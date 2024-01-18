package payment.service.aggregate;

import java.io.Serializable;
import java.util.UUID;

import org.jmolecules.ddd.annotation.ValueObject;

import lombok.Value;

@ValueObject
@Value
public class PaymentId implements Serializable{
    private static final long serialVersionUID=-1;

    private UUID uuid;

}
