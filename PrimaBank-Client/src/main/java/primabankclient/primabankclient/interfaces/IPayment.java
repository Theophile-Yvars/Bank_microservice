package primabankclient.primabankclient.interfaces;

import primabankclient.primabankclient.controllers.dto.PaymentDto;

public interface IPayment {
    boolean payment(PaymentDto paymentDto);
}
