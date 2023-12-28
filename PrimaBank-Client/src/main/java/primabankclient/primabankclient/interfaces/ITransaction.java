package primabankclient.primabankclient.interfaces;

import primabankclient.primabankclient.controllers.dto.PaymentDto;

public interface ITransaction {
    boolean save(PaymentDto paymentDto);
}
