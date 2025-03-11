package uk.gegc.ecommerce.sbecom.service;

import jakarta.transaction.Transactional;
import uk.gegc.ecommerce.sbecom.dto.request.OrderDto;

public interface OrderService {
    @Transactional
    OrderDto placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId, String pgStatus, String pgResponseMessage);
}
