package primabankclient.primabankclient.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import primabankclient.primabankclient.controllers.dto.PaymentDto;
import primabankclient.primabankclient.interfaces.IPayment;
import primabankclient.primabankclient.proxy.TransactionProxy;

@Component
public class Payment implements IPayment {

    TransactionProxy transactionProxy;

    @Autowired
    public Payment(TransactionProxy transactionProxy) {
        this.transactionProxy = transactionProxy;
    }

    @Override
    public boolean payment(PaymentDto paymentDto) {
        return false;
    }
}
