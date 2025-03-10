package uk.gegc.ecommerce.sbecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.gegc.ecommerce.sbecom.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
