package primabankclient.primabankclient.proxy;

import org.springframework.stereotype.Component;
import primabankclient.primabankclient.controllers.dto.PaymentDto;
import primabankclient.primabankclient.interfaces.ITransaction;

@Component
public class TransactionProxy implements ITransaction {
    @Override
    public boolean save(PaymentDto paymentDto) {
        return false;
    }
}
