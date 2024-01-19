package payment.service.aggregate;

import lombok.Value;
import org.jmolecules.ddd.annotation.*;

import java.io.Serializable;
import java.util.UUID;

@ValueObject
@Value
public class AccountId implements Serializable {
    private static final long serialVersionUID = -1455308747700082116L;
    private UUID uuid;
    public String toString() {
        return uuid.toString();
    }


}

